package intelligent_pay.recordservice.controller;

import intelligent_pay.recordservice.command.RecordCommandService;
import intelligent_pay.recordservice.controller.constant.ControllerLog;
import intelligent_pay.recordservice.controller.restResponse.RestResponse;
import intelligent_pay.recordservice.dto.CancelStateRequest;
import intelligent_pay.recordservice.dto.RecordRequest;
import intelligent_pay.recordservice.dto.RecordResponse;
import intelligent_pay.recordservice.query.RecordQueryService;
import intelligent_pay.recordservice.validator.ControllerValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static intelligent_pay.recordservice.controller.constant.RecordParam.*;
import static intelligent_pay.recordservice.controller.constant.RecordUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecordController {

    private final RecordCommandService recordCommandService;
    private final RecordQueryService recordQueryService;
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

    @GetMapping(SEARCH_TITLE)
    public ResponseEntity<?> searchTitle(
            @PathVariable(BANKBOOK_NUM) String bankbookNum,
            @RequestParam(value = KEYWORD, required = false, defaultValue = DEFAULT_KEYWORD) String keyword,
            @RequestParam(value = LAST_ID, required = false, defaultValue = DEFAULT_ID) Long lastId
    ) {
        List<RecordResponse> records = recordQueryService.searchTitle(keyword, bankbookNum, lastId);
        return ResponseEntity.ok(records);
    }

    @PostMapping(DEPOSIT)
    public ResponseEntity<?> deposit(
            @RequestBody @Valid RecordRequest requestDto,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBinding(bindingResult);

        Long depositId = recordCommandService.createDepositRecord(requestDto);
        log.info(ControllerLog.CREATE_DEPOSIT_RECORD.getValue() + depositId);

        return RestResponse.createDepositRecordSuccess(depositId);
    }

    @PostMapping(WITHDRAW)
    public ResponseEntity<?> withdraw(
            @RequestBody @Valid RecordRequest requestDto,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBinding(bindingResult);

        Long withdrawId = recordCommandService.createWithdrawRecord(requestDto);
        log.info(ControllerLog.CREATE_WITHDRAW_RECORD.getValue() + withdrawId);

        return RestResponse.createWithdrawRecordSuccess(withdrawId);
    }

    @PutMapping(CANCEL_STATE)
    public boolean cancelState(
            @RequestBody @Valid CancelStateRequest requestDto,
            BindingResult bindingResult
    ) {
        controllerValidator.validateBindingThrowBool(bindingResult);

        recordCommandService.cancelState(requestDto);
        log.info(ControllerLog.CANCEL_RECORD_SUCCESS.getValue());

        return true;
    }
}
