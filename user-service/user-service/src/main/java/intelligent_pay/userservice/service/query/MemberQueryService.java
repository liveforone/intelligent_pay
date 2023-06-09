package intelligent_pay.userservice.service.query;

import intelligent_pay.userservice.controller.restResponse.ResponseMessage;
import intelligent_pay.userservice.dto.response.MemberResponse;
import intelligent_pay.userservice.exception.MemberCustomException;
import intelligent_pay.userservice.repository.MemberRepository;
import intelligent_pay.userservice.dto.util.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberResponse getMemberByUsername(String username) {
        return MemberMapper.entityToDto(memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberCustomException(ResponseMessage.MEMBER_IS_NULL)));
    }

    public List<MemberResponse> searchByEmail(String email) {
        return memberRepository.searchMemberByEmail(email);
    }
}
