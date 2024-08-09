package com.example.humorie.global.init;

import com.example.humorie.consult_detail.entity.ConsultDetail;
import com.example.humorie.consult_detail.repository.ConsultDetailRepository;
import com.example.humorie.global.config.SecurityConfig;
import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.repository.AccountRepository;
import com.example.humorie.consultant.counselor.entity.*;
import com.example.humorie.consultant.counselor.repository.*;
import com.example.humorie.consultant.review.entity.Review;
import com.example.humorie.consultant.review.repository.ReviewRepository;
import com.example.humorie.mypage.entity.Point;
import com.example.humorie.mypage.repository.PointRepository;
import com.example.humorie.reservation.entity.Reservation;
import com.example.humorie.reservation.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final CounselorRepository counselorRepository;
    private final CounselingMethodRepository methodRepository;
    private final CounselingFieldRepository fieldRepository;
    private final AffiliationRepository affiliationRepository;
    private final EducationRepository educationRepository;
    private final CareerRepository careerRepository;
    private final SymptomRepository symptomRepository;
    private final ReviewRepository reviewRepository;
    private final PointRepository pointRepository;
    private final ReservationRepository reservationRepository;
    private final SecurityConfig securityConfig;
    private final ConsultDetailRepository consultDetailRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        AccountDetail accountDetail1 = accountRepository.findByEmail("test1@naver.com")
                .orElseGet(() -> accountRepository.save(AccountDetail.joinAccount(
                        "test1@naver.com",
                        securityConfig.passwordEncoder().encode("test1234!"),
                        "test123",
                        "김봄")));

        AccountDetail accountDetail2 = accountRepository.findByEmail("test2@naver.com")
                .orElseGet(() -> accountRepository.save(AccountDetail.joinAccount(
                        "test2@naver.com",
                        securityConfig.passwordEncoder().encode("test1234!"),
                        "test234",
                        "김여름")));

        Counselor counselor1 = Counselor.builder()
                .name("김가을")
                .phoneNumber("01000000000")
                .email("rkdmf@naver.com")
                .gender("여성")
                .region("서울시 강남구")
                .rating(4.8)
                .counselingCount(17)
                .reviewCount(8)
                .introduction("편안한 상담사")
                .build();

        Counselor counselor2 = Counselor.builder()
                .name("김겨울")
                .phoneNumber("01011111111")
                .email("rudnf@naver.com")
                .gender("남성")
                .region("서울시 강남구")
                .rating(4.1)
                .counselingCount(30)
                .reviewCount(19)
                .introduction("든든한 상담사")
                .build();

        Counselor counselor3 = Counselor.builder()
                .name("이우정")
                .phoneNumber("01022222222")
                .email("dnwjd@naver.com")
                .gender("여성")
                .region("서울시 강남구")
                .rating(4.5)
                .counselingCount(22)
                .reviewCount(15)
                .introduction("정직한 상담사")
                .build();

        Counselor counselor4 = Counselor.builder()
                .name("명재현")
                .phoneNumber("0103333333")
                .email("audwogus@naver.com")
                .gender("남성")
                .region("경기도 광명시")
                .rating(4.5)
                .counselingCount(50)
                .reviewCount(40)
                .introduction("성실한 상담사")
                .build();

        Counselor counselor5 = Counselor.builder()
                .name("박윤")
                .phoneNumber("01044444444")
                .email("qkrdbs@gmail.com")
                .gender("여성")
                .region("경기도 광명시")
                .rating(4.2)
                .counselingCount(14)
                .reviewCount(7)
                .introduction("편안한 상담사")
                .build();

        Counselor counselor6 = Counselor.builder()
                .name("허윤지")
                .phoneNumber("01055555555")
                .email("gjdbswl@gmail.com")
                .gender("여성")
                .region("경기도 수원시")
                .rating(3.9)
                .counselingCount(7)
                .reviewCount(1)
                .introduction("편안한 상담사")
                .build();

        CounselingMethod method1 = CounselingMethod.builder().method("online").counselor(counselor1).build();
        CounselingMethod method2 = CounselingMethod.builder().method("offline").counselor(counselor1).build();
        CounselingMethod method3 = CounselingMethod.builder().method("online").counselor(counselor2).build();
        CounselingMethod method4 = CounselingMethod.builder().method("offline").counselor(counselor3).build();
        CounselingMethod method5 = CounselingMethod.builder().method("online").counselor(counselor4).build();
        CounselingMethod method6 = CounselingMethod.builder().method("offline").counselor(counselor4).build();
        CounselingMethod method7 = CounselingMethod.builder().method("online").counselor(counselor5).build();
        CounselingMethod method8 = CounselingMethod.builder().method("offline").counselor(counselor6).build();

        CounselingField field1 = CounselingField.builder().field("청소년").counselor(counselor1).build();
        CounselingField field2 = CounselingField.builder().field("개인").counselor(counselor1).build();
        CounselingField field3 = CounselingField.builder().field("청소년").counselor(counselor2).build();
        CounselingField field4 = CounselingField.builder().field("집단").counselor(counselor2).build();
        CounselingField field5 = CounselingField.builder().field("중독").counselor(counselor2).build();
        CounselingField field6 = CounselingField.builder().field("청소년").counselor(counselor3).build();
        CounselingField field7 = CounselingField.builder().field("중독").counselor(counselor4).build();
        CounselingField field8 = CounselingField.builder().field("청소년").counselor(counselor4).build();
        CounselingField field9 = CounselingField.builder().field("개인").counselor(counselor5).build();
        CounselingField field10 = CounselingField.builder().field("집단").counselor(counselor6).build();

        Symptom symptom1 = Symptom.builder().symptom("우울").counselor(counselor1).build();
        Symptom symptom2 = Symptom.builder().symptom("대인관계").counselor(counselor1).build();
        Symptom symptom3 = Symptom.builder().symptom("우울").counselor(counselor2).build();
        Symptom symptom4 = Symptom.builder().symptom("자살").counselor(counselor2).build();
        Symptom symptom5 = Symptom.builder().symptom("사회부적응").counselor(counselor2).build();
        Symptom symptom6 = Symptom.builder().symptom("우울").counselor(counselor3).build();
        Symptom symptom7 = Symptom.builder().symptom("우울").counselor(counselor4).build();
        Symptom symptom8 = Symptom.builder().symptom("대인관계").counselor(counselor4).build();
        Symptom symptom9 = Symptom.builder().symptom("사회부적은").counselor(counselor4).build();
        Symptom symptom10 = Symptom.builder().symptom("우울").counselor(counselor5).build();
        Symptom symptom11 = Symptom.builder().symptom("자살").counselor(counselor6).build();


        Review review1 = Review.builder().title("좋아요").content("좋아요").rating(4.7).createdAt(LocalDateTime.of(2024, 5, 7, 12, 30, 00)).account(accountDetail1).counselor(counselor1).build();
        Review review2 = Review.builder().title("도움돼요").content("도움돼요").rating(4.5).createdAt(LocalDateTime.of(2024, 5, 15, 13, 00, 00)).account(accountDetail1).counselor(counselor1).build();
        Review review3 = Review.builder().title("별로에요").content("별로에요").rating(3.0).createdAt(LocalDateTime.of(2024, 5, 12, 8, 30, 00)).account(accountDetail2).counselor(counselor2).build();
        Review review4 = Review.builder().title("괜찮아요").content("괜찮아요").rating(4.4).createdAt(LocalDateTime.of(2024, 5, 20, 17, 00, 00)).account(accountDetail2).counselor(counselor2).build();
        Review review5 = Review.builder().title("좋아요").content("좋아요").rating(4.7).createdAt(LocalDateTime.of(2024, 5, 7, 12, 30, 00)).account(accountDetail1).counselor(counselor3).build();
        Review review6 = Review.builder().title("도움돼요").content("도움돼요").rating(4.5).createdAt(LocalDateTime.of(2024, 5, 15, 13, 00, 00)).account(accountDetail1).counselor(counselor3).build();
        Review review7 = Review.builder().title("별로에요").content("별로에요").rating(3.0).createdAt(LocalDateTime.of(2024, 5, 12, 8, 30, 00)).account(accountDetail2).counselor(counselor3).build();
        Review review8 = Review.builder().title("괜찮아요").content("괜찮아요").rating(4.4).createdAt(LocalDateTime.of(2024, 5, 20, 17, 00, 00)).account(accountDetail2).counselor(counselor4).build();
        Review review9 = Review.builder().title("좋아요").content("좋아요").rating(4.7).createdAt(LocalDateTime.of(2024, 5, 7, 12, 30, 00)).account(accountDetail1).counselor(counselor4).build();
        Review review10 = Review.builder().title("도움돼요").content("도움돼요").rating(4.5).createdAt(LocalDateTime.of(2024, 5, 15, 13, 00, 00)).account(accountDetail1).counselor(counselor4).build();
        Review review11 = Review.builder().title("별로에요").content("별로에요").rating(3.0).createdAt(LocalDateTime.of(2024, 5, 12, 8, 30, 00)).account(accountDetail2).counselor(counselor5).build();
        Review review12 = Review.builder().title("괜찮아요").content("괜찮아요").rating(4.4).createdAt(LocalDateTime.of(2024, 5, 20, 17, 00, 00)).account(accountDetail2).counselor(counselor5).build();
        Review review13 = Review.builder().title("좋아요").content("좋아요").rating(4.7).createdAt(LocalDateTime.of(2024, 5, 7, 12, 30, 00)).account(accountDetail1).counselor(counselor5).build();
        Review review14 = Review.builder().title("도움돼요").content("도움돼요").rating(4.5).createdAt(LocalDateTime.of(2024, 5, 15, 13, 00, 00)).account(accountDetail1).counselor(counselor6).build();
        Review review15 = Review.builder().title("별로에요").content("별로에요").rating(3.0).createdAt(LocalDateTime.of(2024, 5, 12, 8, 30, 00)).account(accountDetail2).counselor(counselor6).build();
        Review review16 = Review.builder().title("괜찮아요").content("괜찮아요").rating(4.4).createdAt(LocalDateTime.of(2024, 5, 20, 17, 00, 00)).account(accountDetail2).counselor(counselor6).build();

        Affiliation affiliation1 = Affiliation.builder().societyName("학회1").counselor(counselor1).build();
        Affiliation affiliation2 = Affiliation.builder().societyName("학회2").counselor(counselor2).build();
        Affiliation affiliation3 = Affiliation.builder().societyName("학회1").counselor(counselor3).build();
        Affiliation affiliation4 = Affiliation.builder().societyName("학회1").counselor(counselor4).build();
        Affiliation affiliation5 = Affiliation.builder().societyName("학회2").counselor(counselor4).build();
        Affiliation affiliation6 = Affiliation.builder().societyName("학회3").counselor(counselor4).build();
        Affiliation affiliation7 = Affiliation.builder().societyName("학회1").counselor(counselor5).build();
        Affiliation affiliation8 = Affiliation.builder().societyName("학회3").counselor(counselor5).build();
        Affiliation affiliation9 = Affiliation.builder().societyName("학회2").counselor(counselor6).build();

        Education education1 = Education.builder().content("1대학교 심리학 박사").counselor(counselor1).build();
        Education education2 = Education.builder().content("2대학교 심리학 박사").counselor(counselor2).build();
        Education education3 = Education.builder().content("2대학교 심리학 박사").counselor(counselor3).build();
        Education education4 = Education.builder().content("1대학교 심리학 박사").counselor(counselor4).build();
        Education education5 = Education.builder().content("3대학교 심리학 박사").counselor(counselor5).build();
        Education education6 = Education.builder().content("3대학교 심리학 박사").counselor(counselor6).build();

        Career career1 = Career.builder().content("경력1").counselor(counselor1).build();
        Career career2 = Career.builder().content("경력1").counselor(counselor1).build();
        Career career3 = Career.builder().content("경력1").counselor(counselor2).build();
        Career career4 = Career.builder().content("경력1").counselor(counselor3).build();
        Career career5 = Career.builder().content("경력1").counselor(counselor4).build();
        Career career6 = Career.builder().content("경력2").counselor(counselor4).build();
        Career career7 = Career.builder().content("경력1").counselor(counselor5).build();
        Career career8 = Career.builder().content("경력1").counselor(counselor6).build();

        Point point1 = Point.builder().points(100000).type("earn").title("웰컴 포인트").transactionDate(LocalDateTime.of(2024, 5, 7, 12, 30, 00)).account(accountDetail1).build();
        Point point2 = Point.builder().points(50000).type("spend").title("상담 예약").transactionDate(LocalDateTime.of(2024, 5, 10, 12, 30, 00)).account(accountDetail1).build();
        Point point3 = Point.builder().points(1000).type("earn").title("리뷰 작성").transactionDate(LocalDateTime.of(2024, 5, 11, 12, 30, 00)).account(accountDetail1).build();
        Point point4 = Point.builder().points(30000).type("spend").title("상담 예약").transactionDate(LocalDateTime.of(2024, 6, 2, 12, 30, 00)).account(accountDetail1).build();
        Point point5 = Point.builder().points(2000).type("earn").title("리뷰 작성").transactionDate(LocalDateTime.of(2024, 6, 3, 12, 30, 00)).account(accountDetail1).build();
        Point point6 = Point.builder().points(100000).type("earn").title("리뷰 작성").transactionDate(LocalDateTime.of(2024, 5, 9, 12, 30, 00)).account(accountDetail2).build();
        Point point7 = Point.builder().points(70000).type("spend").title("상담 예약").transactionDate(LocalDateTime.of(2024, 5, 17, 12, 30, 00)).account(accountDetail2).build();

        Reservation reservation1 = Reservation.builder().counselDate(LocalDate.of(2024,8,18)).account(accountDetail1).counselTime(LocalTime.of(12,0)).location("서울 강남구").counselor(counselor1).build();
        Reservation reservation2 = Reservation.builder().counselDate(LocalDate.of(2024,8,19)).account(accountDetail2).counselTime(LocalTime.of(12,0)).location("서울 강남구").counselor(counselor2).build();
        Reservation reservation3 = Reservation.builder().counselDate(LocalDate.of(2024,8,20)).account(accountDetail1).counselTime(LocalTime.of(12,0)).location("서울 강남구").counselor(counselor3).build();
        Reservation reservation4 = Reservation.builder().counselDate(LocalDate.of(2024,8,21)).account(accountDetail2).counselTime(LocalTime.of(12,0)).location("서울 강남구").counselor(counselor2).build();

        ConsultDetail consultDetail1 = ConsultDetail.builder()
                .status(true)
                .account(accountDetail1)
                .counselor(counselor1)
                .reservation(reservation1)
                .content("상담 내용 1")
                .symptom("증상 1")
                .title("상담 제목 1")
                .build();

        ConsultDetail consultDetail3 = ConsultDetail.builder()
                .status(false)
                .account(accountDetail1)
                .counselor(counselor3)
                .reservation(reservation3)
                .content("상담 내용 3")
                .symptom("증상 3")
                .title("상담 제목 3")
                .build();


        counselorRepository.saveAll(Arrays.asList(counselor1, counselor2, counselor3, counselor4, counselor5, counselor6));
        methodRepository.saveAll(Arrays.asList(method1, method2, method3, method4, method5, method6, method7, method8));
        fieldRepository.saveAll(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10));
        symptomRepository.saveAll(Arrays.asList(symptom1, symptom2, symptom3, symptom4, symptom5, symptom6, symptom7, symptom8, symptom9, symptom10, symptom11));
        reviewRepository.saveAll(Arrays.asList(review1, review2, review3, review4, review5, review6, review7,  review8, review9, review10, review11, review12, review13, review14, review15, review16));
        affiliationRepository.saveAll(Arrays.asList(affiliation1, affiliation2, affiliation3, affiliation4, affiliation5, affiliation6, affiliation7, affiliation8, affiliation9));
        educationRepository.saveAll(Arrays.asList(education1, education2, education3, education4, education5, education6));
        careerRepository.saveAll(Arrays.asList(career1, career2, career3, career4, career5, career6, career7, career8));
        pointRepository.saveAll(Arrays.asList(point1, point2, point3, point4, point5, point6, point7));
        reservationRepository.saveAll(Arrays.asList(reservation1, reservation2, reservation3, reservation4));
        consultDetailRepository.saveAll(Arrays.asList(consultDetail1, consultDetail3));
    }
}
