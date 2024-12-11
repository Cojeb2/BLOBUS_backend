package project.blobus.Backend.youth.education;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EducationRepository extends JpaRepository<EducationEntity, Integer> {

    EducationEntity findByBizId(String bizId);

    // 필요한 경우 추가 메서드 정의 가능
    @Query("SELECT w FROM EducationEntity w WHERE w.delFlag = false")
    Page<EducationEntity> findAll(Pageable pageable);

    // 제목(title)에서 검색
    @Query("SELECT e FROM EducationEntity e WHERE e.delFlag = false AND e.policyName LIKE %:keyword%")
    Page<EducationEntity> findByPolicyNameContaining(String keyword, Pageable pageable);

    // 내용(overview)에서 검색
    @Query("SELECT e FROM EducationEntity e WHERE e.delFlag = false AND e.policyOverview LIKE %:keyword%")
    Page<EducationEntity> findByPolicyOverviewContaining(String keyword, Pageable pageable);

    // 제목 또는 내용에서 검색
    @Query("SELECT e FROM EducationEntity e WHERE e.delFlag = false AND (e.policyName LIKE %:titleKeyword% OR e.policyOverview LIKE %:overviewKeyword%)")
    Page<EducationEntity> findByPolicyNameContainingOrPolicyOverviewContaining(String titleKeyword, String overviewKeyword, Pageable pageable);

    @Query("SELECT e FROM EducationEntity e WHERE e.delFlag = false AND " +
            "(:category = '유형전체' OR (CASE WHEN :category = '제목' THEN e.policyName LIKE %:keyword% ELSE e.policyOverview LIKE %:keyword% END)) AND " +
            "(:progress = '상태전체' OR (CASE WHEN :progress = '진행중' THEN (e.policyApplicationEndPeriod >= :currentDate OR e.policyApplicationEndPeriod IS NULL) ELSE e.policyApplicationEndPeriod < :currentDate END)) AND " +
            "(e.policyName LIKE %:keyword% OR e.policyOverview LIKE %:keyword%)")
    Page<EducationEntity> findByCategoryAndProgress(@Param("keyword") String keyword,
                                                    @Param("category") String category,
                                                    @Param("progress") String progress,
                                                    @Param("currentDate") LocalDate currentDate,
                                                    Pageable pageable);

    @Modifying
    @Query("UPDATE EducationEntity w SET w.delFlag = :flag WHERE w.policyId = :id")
    void updateToDelete(@Param("id") Integer id, @Param("flag") boolean flag);

}
