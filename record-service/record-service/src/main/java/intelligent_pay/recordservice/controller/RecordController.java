package intelligent_pay.recordservice.controller;

import intelligent_pay.recordservice.authentication.AuthenticationInfo;
import intelligent_pay.recordservice.command.RecordCommandService;
import intelligent_pay.recordservice.query.RecordQueryService;
import intelligent_pay.recordservice.validator.RecordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecordController {

    private final RecordCommandService recordCommandService;
    private final RecordQueryService recordQueryService;
    private final AuthenticationInfo authenticationInfo;
    private final RecordValidator recordValidator;
}
