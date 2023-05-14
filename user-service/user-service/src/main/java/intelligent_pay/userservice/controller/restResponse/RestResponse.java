package intelligent_pay.userservice.controller.restResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> signupSuccess() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseMessage.SIGNUP_SUCCESS.getValue());
    }

    public static ResponseEntity<?> loginSuccess() {
        return ResponseEntity.ok(ResponseMessage.LOGIN_SUCCESS.getValue());
    }

    public static ResponseEntity<?> loginFail() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.LOGIN_FAIL.getValue());
    }

    public static ResponseEntity<?> changeEmailSuccess() {
        return ResponseEntity.ok(ResponseMessage.CHANGE_EMAIL_SUCCESS.getValue());
    }

    public static ResponseEntity<?> changePasswordSuccess() {
        return ResponseEntity.ok(ResponseMessage.CHANGE_PASSWORD_SUCCESS.getValue());
    }

    public static ResponseEntity<?> withdrawSuccess() {
        return ResponseEntity.ok(ResponseMessage.WITHDRAW_SUCCESS.getValue());
    }

    public static ResponseEntity<?> prohibition() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ResponseMessage.PROHIBITION.getValue());
    }
}
