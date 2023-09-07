package UnderTheC.DeepSea.controller;

import UnderTheC.DeepSea.Entity.Lecture;
import UnderTheC.DeepSea.Entity.User;
import UnderTheC.DeepSea.repository.LectureRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@Tag(name = "lecture API", description = "강의 정보 API(미사용)")
@RequestMapping("/lecture")

public class LectureController {
    LectureRepository lectureRepository;

    LectureController(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }




    @GetMapping("")
    @Operation(summary = "강의 모두 찾기", description = "lecture 테이블의 모든 강의 반환", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public List<Lecture> findAll() {
        List<Lecture> lecture = null;
        lecture = lectureRepository.findAll();
        return lecture;
    }



    @PostMapping("")
    @Operation(summary = "강의 추가", description = "lecture 테이블에 지정된 lecture_name으로 강의 추가", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public Lecture addBylecture_name(@RequestParam("lecture_name") String lecture_name) {
        Lecture lecture = null;
        lecture = new Lecture();
        lecture.setLecture_name(lecture_name);
        lecture.setProfessor_name("홍길동");
        lecture.setLecture_divide("전공선택");
        lecture.setSemester_divide("2학기");
        lectureRepository.save(lecture);
        return lecture;
    }





    @DeleteMapping("")
    @Operation(summary = "강의 삭제", description = "lecture 테이블에 지정된 lecture_name으로 유저 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public Lecture deleteBylectrue_name(@RequestParam("lecture_name") String lecture_name) {
        Optional<Lecture> lecture = null;
        lecture = lectureRepository.findById(lecture_name);
        lectureRepository.deleteById(lecture_name);
        return lecture.get();
    }
}