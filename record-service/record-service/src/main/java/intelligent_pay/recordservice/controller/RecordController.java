package intelligent_pay.recordservice.controller;

import intelligent_pay.recordservice.authentication.AuthenticationInfo;
import intelligent_pay.recordservice.command.RecordCommandService;
import intelligent_pay.recordservice.dto.RecordResponse;
import intelligent_pay.recordservice.query.RecordQueryService;
import intelligent_pay.recordservice.validator.ControllerValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static intelligent_pay.recordservice.controller.constant.RecordParam.*;
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
            @PathVariable(ID) Long id
    ) {
        RecordResponse record = recordQueryService.getRecordById(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping(RECORD_HOME)
    public ResponseEntity<?> recordHome(
            @PathVariable(BANKBOOK_NUM) String bankbookNum,
            @RequestParam(value = LAST_ID, required = false, defaultValue = DEFAULT_ID) Long lastId
    ) {
        List<RecordResponse> records = recordQueryService.getRecordsByBankbookNum(bankbookNum, lastId);
        return ResponseEntity.ok(records);
    }

    @GetMapping(DEPOSIT_RECORD)
    public ResponseEntity<?> depositRecord(
            @PathVariable(BANKBOOK_NUM) String bankbookNum,
            @RequestParam(value = LAST_ID, required = false, defaultValue = DEFAULT_ID) Long lastId
    ) {
        List<RecordResponse> records = recordQueryService.getDepositRecords(bankbookNum, lastId);
        return ResponseEntity.ok(records);
    }

    @GetMapping(WITHDRAW_RECORD)
    public ResponseEntity<?> withdrawRecord(
            @PathVariable(BANKBOOK_NUM) String bankbookNum,
            @RequestParam(value = LAST_ID, required = false, defaultValue = DEFAULT_ID) Long lastId
    ) {
        List<RecordResponse> records = recordQueryService.getWithdrawRecords(bankbookNum, lastId);
        return ResponseEntity.ok(records);
    }

    @GetMapping(SEARCH_YEAR)
    public ResponseEntity<?> searchYear(
            @PathVariable(BANKBOOK_NUM) String bankbookNum,
            @RequestParam(value = YEAR, required = false) int year,
            @RequestParam(value = LAST_ID, required = false, defaultValue = DEFAULT_ID) Long lastId
    ) {
        List<RecordResponse> records = recordQueryService.searchYear(year, bankbookNum, lastId);
        return ResponseEntity.ok(records);
    }

    @GetMapping(SEARCH_MONTH)
    public ResponseEntity<?> searchMonth(
            @PathVariable(BANKBOOK_NUM) String bankbookNum,
            @RequestParam(value = YEAR, required = false) int year,
            @RequestParam(value = MONTH, required = false) int month,
            @RequestParam(value = LAST_ID, required = false, defaultValue = DEFAULT_ID) Long lastId
    ) {
        List<RecordResponse> records = recordQueryService.searchMonth(year, month, bankbookNum, lastId);
        return ResponseEntity.ok(records);
    }
}
