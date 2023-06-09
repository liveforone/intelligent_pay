package intelligent_pay.recordservice.controller.query;

import intelligent_pay.recordservice.dto.RecordResponse;
import intelligent_pay.recordservice.service.query.RecordQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static intelligent_pay.recordservice.controller.constant.RecordParam.*;
import static intelligent_pay.recordservice.controller.constant.RecordUrl.*;

@RestController
@RequiredArgsConstructor
public class RecordQueryController {

    private final RecordQueryService recordQueryService;

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
            @RequestParam(value = YEAR, required = false, defaultValue = DEFAULT_INT) int year,
            @RequestParam(value = LAST_ID, required = false, defaultValue = DEFAULT_ID) Long lastId
    ) {
        List<RecordResponse> records = recordQueryService.searchYear(year, bankbookNum, lastId);
        return ResponseEntity.ok(records);
    }

    @GetMapping(SEARCH_MONTH)
    public ResponseEntity<?> searchMonth(
            @PathVariable(BANKBOOK_NUM) String bankbookNum,
            @RequestParam(value = YEAR, required = false, defaultValue = DEFAULT_INT) int year,
            @RequestParam(value = MONTH, required = false, defaultValue = DEFAULT_INT) int month,
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
}
