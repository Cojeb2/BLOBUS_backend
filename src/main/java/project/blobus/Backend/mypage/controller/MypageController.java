package project.blobus.Backend.mypage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.mypage.dto.BookmarkDTO;
import project.blobus.Backend.mypage.dto.CustomDTO;
import project.blobus.Backend.mypage.dto.DocumentdDTO;
import project.blobus.Backend.mypage.service.BookmarkService;
import project.blobus.Backend.mypage.service.CustomService;
import project.blobus.Backend.mypage.service.DocumentService;

import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final CustomService customService;
    private final BookmarkService bookmarkService;
    private final DocumentService documentService;

    // 커스텀 설정 불러오기
    @GetMapping("/custom/setting")
    public Map<String, String> loadCustom(@RequestParam String userId) {
        return customService.loadSetting(userId);
    }

    // 커스텀 설정 저장
    @PostMapping("/custom/setting")
    public void saveCustom(@RequestParam String userId,
                           @RequestParam String yListStr,
                           @RequestParam String rListStr,
                           @RequestParam String kListStr) {
        customService.saveSetting(userId, yListStr, rListStr, kListStr);
    }

    // 커스텀 정보 조회
    @GetMapping("/custom/list")
    public PageResponseDTO<CustomDTO> getCustom(PageRequestDTO pageRequestDTO,
                                                @RequestParam String address,
                                                @RequestParam String yListStr,
                                                @RequestParam String rListStr,
                                                @RequestParam String kListStr) {
        return customService.getList(pageRequestDTO, address, yListStr, rListStr, kListStr);
    }

    // 즐겨찾기 여부 확인
    @GetMapping("bookmark")
    public boolean checkBookmark(@RequestParam String userId,
                                 @RequestParam String main,
                                 @RequestParam String sub,
                                 @RequestParam Long targetId) {
        return bookmarkService.check(userId, main, sub, targetId);
    }

    // 즐겨찾기 변경
    @PostMapping("bookmark")
    public void chagneBookmark(@RequestParam String userId,
                               @RequestParam String main,
                               @RequestParam String sub,
                               @RequestParam Long targetId) {
        bookmarkService.change(userId, main, sub, targetId);
    }

    // 즐겨찾기 목록 조회
    @GetMapping("/bookmark/list")
    public PageResponseDTO<BookmarkDTO> getBookmark(PageRequestDTO pageRequestDTO,
                                                    @RequestParam String userId,
                                                    @RequestParam String category) {
        return bookmarkService.getList(pageRequestDTO, userId, category);
    }

    // 작성글 조회
    @GetMapping("/doc")
    public PageResponseDTO<DocumentdDTO> getCommunity(PageRequestDTO pageRequestDTO,
                                                      @RequestParam String userId,
                                                      @RequestParam String boardType,
                                                      @RequestParam String category) {
        return documentService.getList(pageRequestDTO, userId, boardType, category);
    }
}
