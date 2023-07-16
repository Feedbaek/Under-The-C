package UnderTheC.DeepSea.controller;

import UnderTheC.DeepSea.Entity.Evaluation;
import UnderTheC.DeepSea.repository.EvaluationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam("sortBy") String sortBy
    ) {
        List<Evaluation> evaluation;
        if (sortBy.equals("좋아요수")) {
            evaluation = evaluationRepository.findAllByLectureNameOrderByLikeCountAsc(lectureName);
        } else if (sortBy.equals("최신순")){
            evaluation = evaluationRepository.findAllByLectureNameOrderByCreatedDesc(lectureName);
        }else {
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
    @Operation(summary = "강의 평가 개별 찾기", description = "Evaluation 테이블의 evaluationID로 특정 강의 평가 반환", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public Optional<Evaluation> findByEvaluationID(@RequestParam("evaluationID") String evaluationID) {
        Optional<Evaluation> evaluation = null;
        evaluation = evaluationRepository.findById(evaluationID);
        return evaluation;
    }

    @PostMapping("/add")
    @Operation(summary = "강의 평가 추가", description = "Evaluation 테이블에 강의 평가 추가", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public Evaluation addByEvaluationName(@RequestBody Evaluation evaluationRequest) {
        Evaluation evaluation = new Evaluation();
        // 강의 평가 객체에 evaluationRequest로부터 값을 설정합니다.
        evaluation.setEvaluationID(evaluationRequest.getEvaluationID());
        evaluation.setUserID(evaluationRequest.getUserID());
        evaluation.setLectureName(evaluationRequest.getLectureName());
        evaluation.setProfessorName(evaluationRequest.getProfessorName());
        evaluation.setLectureYear(evaluationRequest.getLectureYear());
        evaluation.setSemesterDivide(evaluationRequest.getSemesterDivide());
        evaluation.setLectureDivide(evaluationRequest.getLectureDivide());
        evaluation.setEvaluationTitle(evaluationRequest.getEvaluationTitle());
        evaluation.setEvaluationContent(evaluationRequest.getEvaluationContent());
        evaluation.setTotalScore(evaluationRequest.getTotalScore());
        evaluation.setCreditScore(evaluationRequest.getCreditScore());
        evaluation.setTotalScore(evaluationRequest.getTotalScore());
        evaluation.setCreditScore(evaluationRequest.getCreditScore());
        evaluation.setComfortableScore(evaluationRequest.getComfortableScore());
        evaluation.setLectureScore(evaluationRequest.getLectureScore());
        evaluation.setLikeCount(evaluationRequest.getLikeCount());
        evaluation.setCreated(evaluationRequest.getCreated());
        evaluationRepository.save(evaluation);
        return evaluation;
    }
    @PutMapping("/update/{id}")
    @Operation(summary = "강의 평가 수정", description = "evaluationID 입력받아 Evaluation 테이블의 강의 평가 수정", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public Evaluation updateEvaluation(
            @PathVariable("id") String evaluationID,
            @RequestBody Evaluation evaluationRequest
    ) {

        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(evaluationID);
        if (optionalEvaluation.isPresent()) {
            Evaluation evaluation = optionalEvaluation.get();

            // 강의 평가 객체에 evaluationRequest로부터 값을 업데이트합니다.
            evaluation.setLectureName(evaluationRequest.getLectureName());
            evaluation.setProfessorName(evaluationRequest.getProfessorName());
            evaluation.setLectureYear(evaluationRequest.getLectureYear());
            evaluation.setSemesterDivide(evaluationRequest.getSemesterDivide());
            evaluation.setLectureDivide(evaluationRequest.getLectureDivide());
            evaluation.setEvaluationTitle(evaluationRequest.getEvaluationTitle());
            evaluation.setEvaluationContent(evaluationRequest.getEvaluationContent());
            evaluation.setTotalScore(evaluationRequest.getTotalScore());
            evaluation.setCreditScore(evaluationRequest.getCreditScore());
            evaluation.setComfortableScore(evaluationRequest.getComfortableScore());
            evaluation.setLectureScore(evaluationRequest.getLectureScore());
            evaluation.setLikeCount(evaluationRequest.getLikeCount());
            evaluation.setUpdated(evaluationRequest.getUpdated());

            evaluationRepository.save(evaluation);

            return evaluation;
        } else {
            System.out.println("예외처리");
            return null;
            // 평가 ID에 해당하는 객체가 없는 경우 예외 처리
            //throw new NotFoundException("평가 ID에 해당하는 객체를 찾을 수 없습니다.");
        }
    }



/*
    @PostMapping("/update/{id}")
    @Operation(summary = "강의 평가 수정", description = "Evaluation 테이블에 강의 평가 수정", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })

    public Evaluation updateEvaluation(
            @RequestParam("evaluationID") String evaluationID,
            String lectureName,
            String professorName,
            int lectureYear,
            String semesterDivide,
            String lectureDivide,
            String evaluationTitle,
            String evaluationContent,
            String totalScore,
            String creditScore,
            String comfortableScore,
            String lectureScore,
            int likeCount
    )*/

    @DeleteMapping("/delete")
    @Operation(summary = "강의평가 삭제", description = "Evaluation 테이블에 지정된 evaluationID로 강의평가 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public Evaluation deleteByLectureName(@RequestParam("evluationID") String evaluationID) {
        Optional<Evaluation> evaluation = null;
        evaluation = evaluationRepository.findById(evaluationID);
        evaluationRepository.deleteById(evaluationID);
        return evaluation.get();
    }
}