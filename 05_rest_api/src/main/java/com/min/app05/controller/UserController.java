package com.min.app05.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.app05.model.ResponseErrorMessage;
import com.min.app05.model.ResponseMessage;
import com.min.app05.model.SortEnum;
import com.min.app05.model.dto.InsertUserDto;
import com.min.app05.model.dto.UpdateUserDto;
import com.min.app05.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/*
 * Swagger 설정 Annotation
 *  @Tag
 *  @Operation
 *  @ApiResponse
 *  @Parameter
 */

@Tag(name = "API 목록", description = "회원 관리 API")
// REST API 서비스 개발을 위한 컨트롤러 : @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;
  
  // @Validated : 유효성 검사를 수행할 객체에 추가, 실제 유효성 검사는 해당 객체(UserDto)에서 수행한다.
  // @RequestBody : 요청 본문을 의미
  // @ApiResponse : api 메소드에서 반환하는 여러 응답 코드에 대한 설명을 지정하는 Annotation
  @Operation(summary = "신규 회원 등록", description = "이메일, 비밀번호, 닉네임을 이용하는 신규 회원 등록 기능입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "신규 회원 등록 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      
      // 에러 코드의 경우, 만들어진 ResponseErrorMessage class가 하나밖에 없기 때문에, Swagger에서 Example value 내용이 하나로 통일됨
      // 각 코드마다 example을 각자 지정해서 다른 내용으로 나오게끔 처리
      // """ (3중 지원) 최근 기능 활용
    , @ApiResponse(responseCode = "00", description = "기존 회원과 동일한 이메일로 등록 시도", content = @Content(schema = @Schema(example = """
      {
        "code" : "00",
        "message" : "중복된 키 입력",
        "describe" : "기존 회원과 동일한 이메일이 입력되었습니다."
      }
      """)))
      // 위 양식을 메모장에 넣어둔 후, "" 만 입력한 상태에서 복붙하면 알아서 처리됨
    , @ApiResponse(responseCode = "01", description = "필수 값 누락 또는 공백으로 등록 시도", content = @Content(schema = @Schema(example = "{\n"
        + "  \"code\" : \"01\",\n"
        + "  \"message\" : \"잘못된 키 입력\",\n"
        + " \"describe\" : \"필수 값이 누락되거나 공백이 입력되었습니다.\"\n"
        + "}")))
    , @ApiResponse(responseCode = "02", description =  "정해진 크기에서 벗어난 값으로 등록 시도", content =  @Content(schema = @Schema(example = "{\n"
        + "  \"code\" : \"02\",\n"
        + "  \"message\" : \"정해진 크기를 벗어난 키 입력\",\n"
        + " \"describe\" : \"정해진 크기를 벗어난 값이 입력되었습니다.\"\n"
        + "}")))
  
  })
  
  @PostMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseMessage registUser(@Validated @RequestBody InsertUserDto insertUserDto) throws Exception {
    return ResponseMessage.builder()
              .status(201) // 201 Created (post 요청이 성공적으로 처리 되었으며, 자원이 생성되었음을 나타내는 성공 상태 응답 코드) 
              .message("회원 등록 성공")
              .results(Map.of("user", userService.registUser(insertUserDto)))
              .build();
  }
  
  @Operation(summary = "회원 정보 수정", description = "닉네임, 비밀번호를 수정하는 회원 정보 수정 기능입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "회원 정보 수정 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
   ,  @ApiResponse(responseCode = "01", description = "필수 입력 값 누락", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
   ,  @ApiResponse(responseCode = "02", description = "크기를 벗어난 값 입력", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
   ,  @ApiResponse(responseCode = "404", description = "회원 정보 없음", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
   ,  @ApiResponse(responseCode = "03", description = "잘못된 타입의 경로 변수 전달", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
   ,  @ApiResponse(responseCode = "05", description = "경로 변수 누락", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
  })
  @PutMapping(value="/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseMessage modifyUser(@PathVariable int userId, @Validated @RequestBody UpdateUserDto updateUserDto) throws Exception {
    updateUserDto.setUserId(userId);
    return ResponseMessage.builder()
              .status(201)  // 수정 또한 삽입과 동일한 응답 코드를 사용합니다.
                            // 200 OK 를 사용해도 무방합니다.
              .message("회원 정보 수정 성공")
              .results(Map.of("user", userService.modifyUser(updateUserDto)))
              .build();


  }
  @Operation(summary = "회원 정보 삭제", description = "회원 번호가 일치하는 회원의 정보 삭제 기능입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "회원 정보 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    , @ApiResponse(responseCode = "404", description = "회원 정보 없음", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "03", description = "잘못된 타입의 경로 변수 전달", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "05", description = "경로 변수 누락", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))  
  })
  @DeleteMapping(value="/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseMessage removeUser(@PathVariable int userId) throws Exception {
    userService.removeUser(userId);
    return ResponseMessage.builder()
              .status(204)  // 요청이 성공하였으나 해당 데이터를 참조할 수 없음을 의미합니다.
                            // 삭제 후 204를 사용할 수 있으나 실제로는 주로 200을 사용합니다.
              .message("회원 삭제 성공")
              .results(null)
            .build();
  }
  
  @Operation(summary = "회원 목록 조회", description = "page, display, sort 에 따른 회원 목록을 조회하는 기능입니다.")
  @ApiResponses(value= {
      @ApiResponse(responseCode = "200", description = "목록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    , @ApiResponse(responseCode = "06", description = "잘못된 파라미터로 조회 시도", content = @Content(schema = @Schema(example = """
        {
          "code" : "06",
          "message" : "파라미터 오류",
          "describe" : "잘못된 요청 파라미터 입니다."
        }
        """)))
    , @ApiResponse(responseCode = "07", description = "잘못된 파라미터 값으로 조회 시도", content = @Content(schema = @Schema(example = """
        {
          "code" : "07",
          "message" : "파라미터 값 오류",
          "describe" : "파라미터 sort의 값이 잘못 전달 되었습니다."
        }
        """)))
  })
  
  @Parameters(value= {
      @Parameter(name="page", required = true, description = "조회할 페이지 번호"),
      @Parameter(name="display", required = true, description = "페이지에 포함할 회원 수"),
      @Parameter(name="sort", required = true, description = "회원 정렬 방식", schema = @Schema(implementation = SortEnum.class))
  })
  
  @GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseMessage getUsers(HttpServletRequest request) {
    return ResponseMessage.builder()
              .status(200)  // 요청이 성공하여 가져온 리소스를 메시지 본문으로 전송하였습니다.
              .message("회원 목록 조회 성공")
              .results(Map.of("users", userService.getUsers(request)))
            .build();
  }
  
  @Operation(summary = "회원 상세조회", description = "회원 번호에 따른 회원 상세 정보를 조회하는 기능입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "회원 조회 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    , @ApiResponse(responseCode = "404", description = "회원 정보 없음", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "03", description = "잘못된 타입의 경로 변수 전달", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "05", description = "경로 변수 누락", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
  })
  @GetMapping(value="/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseMessage getUserById(@PathVariable int userId) throws Exception {
    return ResponseMessage.builder()
              .status(200)  // 요청이 성공하여 가져온 리소스를 메시지 본문으로 전송하였습니다.
              .message("회원 조회 성공")
              .results(Map.of("user", userService.getUserById(userId)))
            .build();
  }
   
}
