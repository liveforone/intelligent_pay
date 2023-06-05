package intelligent_pay.userservice.query;

import intelligent_pay.userservice.dto.response.MemberResponse;
import intelligent_pay.userservice.repository.MemberRepository;
import intelligent_pay.userservice.query.util.MemberMapper;
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
        return MemberMapper.entityToDto(memberRepository.findByUsername(username));
    }

    public List<MemberResponse> searchByEmail(String email) {
        return memberRepository.searchMemberByEmail(email);
    }
}
