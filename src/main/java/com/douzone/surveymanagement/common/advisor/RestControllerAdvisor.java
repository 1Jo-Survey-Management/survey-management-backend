package com.douzone.surveymanagement.common.advisor;


import com.douzone.surveymanagement.common.exception.BadRequestException;
import com.douzone.surveymanagement.common.exception.NotFoundElementException;
import com.douzone.surveymanagement.common.response.CommonResponse;
import com.douzone.surveymanagement.common.response.ErrorResponse;
import com.douzone.surveymanagement.common.response.MethodArgumentValidError;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 컨트롤러단에서 발생하는 에러를 처리하기 위한 ControllerAdvice 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
@Slf4j
@RestControllerAdvice
public class RestControllerAdvisor {

    /**
     * MethodArgumentNotValidException 을 처리하기 위한 ExceptionHandler 입니다.
     *
     * @param e MethodArgumentNotValidException
     * @return HttpStatus: BAD_REQUEST, 공통 읍답객체에 ErrorResponse 객체를 담은 응답입니다.
     * @author : 강명관
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<ErrorResponse>> methodArgumentValidExceptionHandler(
        MethodArgumentNotValidException e) {

        log.error("MethodArgumentNotValidException Error");
        log.error("message : {}", e.getMessage());
        List<MethodArgumentValidError> methodArgumentValidErrors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new MethodArgumentValidError(
                error.getField(),
                error.getRejectedValue(),
                error.getDefaultMessage()
            ))
            .collect(Collectors.toList());

        return ResponseEntity.badRequest()
            .body(CommonResponse.error(
                    ErrorResponse.ofMethodArgumentValidError(methodArgumentValidErrors)
                )
            );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse<ErrorResponse>> badRequestExceptionHandler(
        BadRequestException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(CommonResponse.error(ErrorResponse.of(e.getMessage())));
    }

    /**
     * 요소를 찾지 못했을때를 처리하기 위한 공통에러인 NotFoundElementException 을 처리하기 위한 핸들러 입니다.
     *
     * @param e NotFoundElementException
     * @return HttpStatus: NOT_FOUND, 공통 읍답객체에 ErrorResponse 객체를 담은 응답입니다..
     * @author : 강명관
     */
    @ExceptionHandler(value = {NotFoundElementException.class})
    public ResponseEntity<CommonResponse<ErrorResponse>> notFoundElementExceptionHandler(
        NotFoundElementException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(CommonResponse.error(ErrorResponse.of(e.getMessage())));
    }

    /**
     * 처리하지 못한 전역 에러를 처리하기 위한 ExceptionHandler 입니다.
     *
     * @param e Exception
     * @return 공통 응답객체에 ErrorResponse 객체를 담은 응답입니다.
     * @author : 강명관
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<CommonResponse<ErrorResponse>> internalServerErrorHandler(Exception e) {

        log.error("Internal Server Error");
        log.error("message : {} ", e.getMessage());

        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(CommonResponse.error(ErrorResponse.of(e.getMessage())));
    }
}
