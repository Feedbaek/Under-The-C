package UnderTheC.DeepSea.controller;

import UnderTheC.DeepSea.Entity.Evaluation;
import UnderTheC.DeepSea.Entity.User;
import UnderTheC.DeepSea.repository.EvaluationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Evaluation API", description = "강의 평가 API")
@RequestMapping("/evaluation")

public class EvaluationController {
    EvaluationRepository evaluationRepository;

    EvaluationController(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @GetMapping("/search")
    @Operation(summary = "강의 평가 검색 (최신순 or 좋아요순)", description = "Evaluation 테이블에서 lectureName으로 검색하여 " +
            "최신순 또는 'likeCount' 오름차순으로 정렬된 Evaluation 객체 반환", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public List<Evaluation> searchByLectureName(
            @RequestParam("lectureName") String lectureName,
            @RequestParam("lectureDivide") String lectureDivide,
            @RequestParam("sortBy") String sortBy
    ) {
        List<Evaluation> evaluation;
        if (sortBy.equals("좋아요순")) {
            if (lectureDivide != null && !lectureDivide.isEmpty()) {
                evaluation = evaluationRepository.findAllByLectureNameAndLectureDivideOrderByLikeCountDesc(lectureName, lectureDivide);
            } else {
                evaluation = evaluationRepository.findAllByLectureNameOrderByLikeCountDesc(lectureName);
            }
        } else if (sortBy.equals("최신순")) {
            if (lectureDivide != null && !lectureDivide.isEmpty()) {
                evaluation = evaluationRepository.findAllByLectureNameAndLectureDivideOrderByCreatedDesc(lectureName, lectureDivide);
            } else {
                evaluation = evaluationRepository.findAllByLectureNameOrderByCreatedDesc(lectureName);
            }
        } else {
            evaluation = evaluationRepository.findByLectureName(lectureName);
        }
        return evaluation;
    }

    @GetMapping("/find")
    @Operation(summary = "강의 평가 모두 찾기", description = "Evaluation 테이블의 모든 강의 평가 반환", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public List<Evaluation> findAll() {
        List<Evaluation> evaluation = null;
        evaluation = evaluationRepository.findAll();
        return evaluation;
    }


    @GetMapping("/view")
    @Operation(summary = "강의 평가 개별 찾기", description = "Evaluation 테이블의 evaluationId로 특정 강의 평가 반환", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public Optional<Evaluation> findByEvaluationId(@RequestParam("evaluationId") String evaluationId) {
        Optional<Evaluation> evaluation = null;
        evaluation = evaluationRepository.findById(evaluationId);
        return evaluation;
    }

    @PostMapping("/add")
    @Operation(summary = "강의 평가 추가", description = "Evaluation 테이블에 강의 평가 추가", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public Evaluation addByEvaluationName( HttpServletRequest request,@RequestBody Evaluation evaluationRequest) {



        if (request.getSession(false) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인이 필요합니다."); // 로그인되지 않은 경우 예외 처리
        }
        // 세션에서 로그인 정보를 가져옴
        User user = (User)request.getSession(false).getAttribute("loginUser");
        String userId = user.getId();

        // 검색 조건으로 사용할 lectureName
        String lectureName = evaluationRequest.getLectureName();

        // userId와 lectureName을 동시에 충족하는 Evaluation 객체 검색
        Optional<Evaluation> existingEvaluation = evaluationRepository.findByUserIdAndLectureName(userId, lectureName);
        if (existingEvaluation.isPresent()) {
            // 이미 해당 조건의 평가가 존재하는 경우 예외 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "같은 사용자와 강의명으로 작성된 평가가 이미 존재합니다.");
        }
        Evaluation evaluation = new Evaluation();
        // 강의 평가 객체에 evaluationRequest로부터 값을 설정합니다.
        evaluationRequest.setUserId(userId);
        evaluationRepository.save(evaluationRequest);

        return evaluationRequest;
    }

    @PatchMapping("/update/{id}")
    @Operation(summary = "강의 평가 수정", description = "evaluationId 입력받아 Evaluation 테이블의 강의 평가 수정", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public Evaluation updateEvaluation(
            @PathVariable("id") String evaluationId,
            @RequestBody Evaluation evaluationRequest, HttpServletRequest request
    ) {

        if (request.getSession(false) == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        User user = new User();
        user = (User)request.getSession(false).getAttribute("loginUser");
        String userId = user.getId();

        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(evaluationId);
        if (optionalEvaluation.isPresent()) {
            Evaluation evaluation = optionalEvaluation.get();

            if (!evaluation.getUserId().equals(userId)) {
                throw new IllegalArgumentException("해당 평가의 작성자만 수정할 수 있습니다.");
            }
            // 강의 평가 객체에 evaluationRequest로부터 값을 업데이트합니다.
            if (evaluationRequest.getLectureName() != null)
                evaluation.setLectureName(evaluationRequest.getLectureName());
            if (evaluationRequest.getProfessorName() != null)
                evaluation.setProfessorName(evaluationRequest.getProfessorName());
            if (evaluationRequest.getLectureYear() != 0)
                evaluation.setLectureYear(evaluationRequest.getLectureYear());
            if (evaluationRequest.getSemesterDivide() != null)
                evaluation.setSemesterDivide(evaluationRequest.getSemesterDivide());
            if (evaluationRequest.getLectureDivide() != null)
                evaluation.setLectureDivide(evaluationRequest.getLectureDivide());
            if (evaluationRequest.getEvaluationTitle() != null)
                evaluation.setEvaluationTitle(evaluationRequest.getEvaluationTitle());
            if (evaluationRequest.getEvaluationContent() != null)
                evaluation.setEvaluationContent(evaluationRequest.getEvaluationContent());
            if (evaluationRequest.getTotalScore() != null)
                evaluation.setTotalScore(evaluationRequest.getTotalScore());
            if (evaluationRequest.getCreditScore() != null)
                evaluation.setCreditScore(evaluationRequest.getCreditScore());
            if (evaluationRequest.getComfortableScore() != null)
                evaluation.setComfortableScore(evaluationRequest.getComfortableScore());
            if (evaluationRequest.getLectureScore() != null)
                evaluation.setLectureScore(evaluationRequest.getLectureScore());
            if (evaluationRequest.getLikeCount() != 0)
                evaluation.setLikeCount(evaluationRequest.getLikeCount());
            if (evaluationRequest.getUpdated() != null)
                evaluation.setUpdated(evaluationRequest.getUpdated());
            evaluationRepository.save(evaluation);
            return evaluation;
        } else {
            //예외처리
            throw new IllegalArgumentException("평가 Id에 해당하는 객체를 찾을 수 없습니다.");
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "강의평가 삭제", description = "Evaluation 테이블에 지정된 evaluationId로 강의평가 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public Evaluation deleteByLectureName(@RequestParam("evaluationId") String evaluationId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        User user = new User();
        user = (User)request.getSession(false).getAttribute("loginUser");

        String userId = user.getId();

        Optional<Evaluation> evaluation = evaluationRepository.findById(evaluationId);

        if (evaluation.isPresent()) {
            if (!evaluation.get().getUserId().equals(userId)) {
                throw new IllegalArgumentException("해당 평가의 작성자만 삭제할 수 있습니다.");
            }
            evaluationRepository.deleteById(evaluationId);
            return evaluation.get();
        } else {
            throw new IllegalArgumentException("평가 ID에 해당하는 객체를 찾을 수 없습니다.");
        }
    }

    @PostConstruct
    public void init() {

        for (int i=0; i<10; ++i) {
            Evaluation evaluation = new Evaluation();

            evaluation.setEvaluationId(i);
            evaluation.setUserId("test1");
            evaluation.setLectureName("string");
            evaluation.setProfessorName("string");
            evaluation.setLectureYear(0);
            evaluation.setSemesterDivide("string");
            evaluation.setLectureDivide("string");
            evaluation.setEvaluationTitle("string");
            evaluation.setEvaluationContent("string");
            evaluation.setTotalScore("string");
            evaluation.setCreditScore("string");
            evaluation.setComfortableScore("string");
            evaluation.setLectureScore("string");
            evaluation.setLikeCount(0);
            evaluation.setCreated(new Date());
            evaluation.setUpdated(new Date());

            this.evaluationRepository.save(evaluation);
        }

        for (int i=10; i<20; ++i) {
            Evaluation evaluation = new Evaluation();

            evaluation.setEvaluationId(i);
            evaluation.setUserId("test2");
            evaluation.setLectureName("string");
            evaluation.setProfessorName("string");
            evaluation.setLectureYear(0);
            evaluation.setSemesterDivide("string");
            evaluation.setLectureDivide("string");
            evaluation.setEvaluationTitle("string");
            evaluation.setEvaluationContent("string");
            evaluation.setTotalScore("string");
            evaluation.setCreditScore("string");
            evaluation.setComfortableScore("string");
            evaluation.setLectureScore("string");
            evaluation.setLikeCount(0);
            evaluation.setCreated(new Date());
            evaluation.setUpdated(new Date());

            this.evaluationRepository.save(evaluation);
        }

        for (int i=20; i<30; ++i) {
            Evaluation evaluation = new Evaluation();

            evaluation.setEvaluationId(i);
            evaluation.setUserId("test3");
            evaluation.setLectureName("string");
            evaluation.setProfessorName("string");
            evaluation.setLectureYear(0);
            evaluation.setSemesterDivide("string");
            evaluation.setLectureDivide("string");
            evaluation.setEvaluationTitle("string");
            evaluation.setEvaluationContent("string");
            evaluation.setTotalScore("string");
            evaluation.setCreditScore("string");
            evaluation.setComfortableScore("string");
            evaluation.setLectureScore("string");
            evaluation.setLikeCount(0);
            evaluation.setCreated(new Date());
            evaluation.setUpdated(new Date());

            this.evaluationRepository.save(evaluation);
        }
    }
}