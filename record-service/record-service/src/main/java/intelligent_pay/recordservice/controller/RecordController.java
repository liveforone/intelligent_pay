package intelligent_pay.recordservice.controller;

import intelligent_pay.recordservice.authentication.AuthenticationInfo;
import intelligent_pay.recordservice.command.RecordCommandService;
import intelligent_pay.recordservice.controller.constant.RecordParam;
import intelligent_pay.recordservice.dto.RecordResponse;
import intelligent_pay.recordservice.query.RecordQueryService;
import intelligent_pay.recordservice.validator.ControllerValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static intelligent_pay.recordservice.controller.constant.RecordUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecordController {

    private final RecordCommandService recordCommandService;
    private final RecordQueryService recordQueryService;
    private final AuthenticationInfo authenticationInfo;
    private final ControllerValidator controllerValidator;

    @GetMapping(RECORD_DETAIL)
    public ResponseEntity<?> recordDetail(
            @PathVariable(RecordParam.ID) Long id
    ) {
        RecordResponse record = recordQueryService.getRecordById(id);

        return ResponseEntity.ok(record);
    }
}
