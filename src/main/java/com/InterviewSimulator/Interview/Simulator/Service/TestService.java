package com.InterviewSimulator.Interview.Simulator.Service;

import com.InterviewSimulator.Interview.Simulator.DTO.Test.QuestionsDto;
import com.InterviewSimulator.Interview.Simulator.DTO.Test.SectionsDto;
import com.InterviewSimulator.Interview.Simulator.DTO.Test.TestDto;
import com.InterviewSimulator.Interview.Simulator.DTO.Test.TestResponse.QuestionResponseDto;
import com.InterviewSimulator.Interview.Simulator.DTO.Test.TestResponse.SectionResponseDto;
import com.InterviewSimulator.Interview.Simulator.DTO.Test.TestResponse.TestCasesResponseDto;
import com.InterviewSimulator.Interview.Simulator.DTO.Test.TestResponse.TestResponseDto;
import com.InterviewSimulator.Interview.Simulator.DTO.Test.Test_casesDto;
import com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission.AnswerDto;
import com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission.CodeRunDto;
import com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission.TestResultDto;
import com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission.TestSubmitRequestDto;
import com.InterviewSimulator.Interview.Simulator.Entity.*;
import com.InterviewSimulator.Interview.Simulator.Enumerations.QuestionType;
import com.InterviewSimulator.Interview.Simulator.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TestService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestRepository testRepository;
    @Autowired
    GeminiService geminiService;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    Test_caseRepository test_caseRepository;
    @Autowired
    AttemptRepository  attemptRepository;
    @Autowired
      GroqService groqService;

    @Autowired
    Judge0Service judge0Service;

     public TestDto generateTest(){

         String RawTest = geminiService.generateQuestions();

         String refinedTest  = RawTest
                     .replace("```json", "")
                     .replace("```", "")
                     .trim();
         System.out.println(refinedTest);

         ObjectMapper objectMapper = new ObjectMapper();
         TestDto testDto = objectMapper.readValue(refinedTest, TestDto.class);
         saveTest(testDto);
         return testDto;


     }

    @Transactional
    public void saveTest(TestDto dto) {


        Test test = new Test();
        test.setTest_title(dto.getTest_title());
        test.setCreatedAt(LocalDateTime.now());
        test.setDate(LocalDate.now());

        List<Sections> sectionList = new ArrayList<>();


        for (SectionsDto sectionDto : dto.getSections()) {

            Sections section = new Sections();
            section.setSection_title(sectionDto.getSection_title());
            section.setSection_type(sectionDto.getSection_type());
            section.setInstructions(sectionDto.getInstructions());
            section.setTest(test);

            List<Question> questionList = new ArrayList<>();


            for (QuestionsDto qDto : sectionDto.getQuestionsDtoList()) {

                Question question = new Question();
                question.setSection(section);


                if ("mcq".equalsIgnoreCase(sectionDto.getSection_type())) {

                    question.setQuestionText(qDto.getQuestion());
                    question.setType(QuestionType.MCQ);
                    question.setTest(test);

                    List<Options> optionList = new ArrayList<>();

                    if (qDto.getOptions() != null) {
                        for (String optStr : qDto.getOptions()) {

                            Options option = new Options();
                            option.setOptionText(optStr);
                            option.setQuestion(question);


                            if (optStr.equals(qDto.getCorrect_answer())) {
                                option.setisCorrect(true);
                            } else {
                                option.setisCorrect(false);
                            }

                            optionList.add(option);
                        }
                    }

                    question.setOptions(optionList);
                }

                String type = sectionDto.getSection_type().toLowerCase();

                if (type.contains("coding"))  {

                    question.setQuestionText(
                            qDto.getTitle() != null ? qDto.getTitle() : "Coding Question"
                    );

                    question.setDescription(
                            qDto.getDescription() != null ? qDto.getDescription() : ""
                    );

                    question.setType(QuestionType.CODING);
                    question.setTest(test);

                    List<Test_cases> testCaseList = new ArrayList<>();

                    if (qDto.getTest_cases() != null) {

                        for (Test_casesDto tcDto : qDto.getTest_cases()) {

                            Test_cases tc = new Test_cases();

                            tc.setInput(tcDto.getInput());
                            tc.setExpectedoutput(tcDto.getExpected_output());

                            tc.setIsHidden(
                                    tcDto.getIsHidden() != null ? tcDto.getIsHidden() : false
                            );

                            tc.setQuestion(question);
                            testCaseList.add(tc);
                        }
                    }

                    question.setTestcases(testCaseList);
                }

                else if ("theory".equalsIgnoreCase(sectionDto.getSection_type())) {

                    question.setQuestionText(qDto.getQuestion());
                    question.setType(QuestionType.THEORY);
                    question.setTest(test);
                }

                questionList.add(question);
            }

            section.setQuestions(questionList);
            sectionList.add(section);
        }

        test.setSections(sectionList);


       testRepository.save(test);
    }

    public TestResponseDto getTodayTest() {
         Test test = testRepository.findTopByDateOrderByCreatedAtDesc(LocalDate.now()).orElseThrow(()-> new RuntimeException("not test published yet"));

         return convertToResponse(test);

    }
    public TestResponseDto convertToResponse(Test test) {
        TestResponseDto dto = new TestResponseDto();
        dto.setTest_title(test.getTest_title());

        List<SectionResponseDto> sectionDtos = new ArrayList<>();

        for (Sections section : test.getSections()) {

            SectionResponseDto sDto = new SectionResponseDto();
            sDto.setSectionTitle(section.getSection_title());
            sDto.setSectionType(section.getSection_type());
            sDto.setInstructions(section.getInstructions());

            List<QuestionResponseDto> qDtos = new ArrayList<>();

            for (Question q : section.getQuestions()) {

                QuestionResponseDto qDto = new QuestionResponseDto();
                qDto.setQuestionId(q.getId());

                if (q.getType() == QuestionType.MCQ) {
                    qDto.setQuestion(q.getQuestionText());

                    List<String> opts = q.getOptions()
                            .stream()
                            .map(Options::getOptionText)
                            .toList();

                    qDto.setOptions(opts);
                }

                else if (q.getType() == QuestionType.CODING) {
                    qDto.setTitle(q.getQuestionText());
                    qDto.setDescription(q.getDescription());
                    List<TestCasesResponseDto> visibleTestCases = q.getTestcases()
                            .stream()
                            .filter(tc -> tc.isHidden()) // only visible
                            .map(tc -> {
                                TestCasesResponseDto tcDto = new TestCasesResponseDto();
                                tcDto.setInput(tc.getInput());
                                tcDto.setExpected_output(tc.getExpectedoutput());
                                return tcDto;
                            })
                            .toList();

                    qDto.setTestcases(visibleTestCases);
                }

                else if (q.getType() == QuestionType.THEORY) {
                    qDto.setQuestion(q.getQuestionText());
                }

                qDtos.add(qDto);
            }

            sDto.setQuestions(qDtos);
            sectionDtos.add(sDto);
        }

        dto.setSections(sectionDtos);

        return dto;
    }

    @Transactional
    public TestResultDto  getTodayTestResult(TestSubmitRequestDto dto) {
        long id = dto.getTestId();
        Test test = testRepository.findById(id).orElseThrow(() -> new RuntimeException("Test not published yet"));
           User user = userRepository.findByname(getLoggedInUser()).orElseThrow(()-> new RuntimeException("User not found"));

        Attempt attempt = new Attempt();

        attempt.setUser(user);
        attempt.setTest(test);
        attempt.setStartedAt(LocalDateTime.now());
        attempt.setSubmittedAt(LocalDateTime.now());

        int totalscore = 0;
        int codingscore = 0;
        int mcqscore = 0;
        int theoryscore = 0;

        for (AnswerDto answer : dto.getAnswer()) {

            Question question = questionRepository.findById(answer.getQuestion_id())
                    .orElseThrow();

            Submission submission = new Submission();
            submission.setAttempt(attempt);
            submission.setQuestion(question);


            if (question.getType() == QuestionType.MCQ) {

                submission.setSelectedOption(answer.getOption());


                String correctOption = question.getOptions()
                        .stream()
                        .filter(Options::getIsCorrect)
                        .findFirst()
                        .map(Options::getOptionText)
                        .orElse(null);

                if (correctOption != null &&
                        correctOption.equals(answer.getOption())) {

                    submission.setIsCorrect(true);
                    totalscore ++;
                    mcqscore++;
                } else {
                    submission.setIsCorrect(false);
                }
            }

            // CODING
            else if (question.getType() == QuestionType.CODING) {
                String code = answer.getCode();

                List<Test_cases> testcases = question.getTestcases();

                int passed = 0;

                for (Test_cases tc : testcases) {
                String cleanInput = sanitizeInput(tc.getInput());

                    String output = judge0Service.runCode(code,cleanInput);
                    System.out.println("OUTPUT FROM JUDGE: " + output);
                     if(output == null)continue;

                    if (output.startsWith("COMPILE_ERROR") ||
                            output.startsWith("RUNTIME_ERROR") ||
                            output.equals("TLE") ||
                            output.equals("FAILED")) {

                        continue;
                    }
                    if (output != null &&
                            output.trim().equals(tc.getExpectedoutput().trim())) {
                        passed++;
                    }
                }
              if(passed == testcases.size()){
                totalscore += 10;
                codingscore += 10;
            }}


            else if (question.getType() == QuestionType.THEORY) {
                 submission.setAnswerText(answer.getAnswer());
                int score = groqService.evaluate(
                        question.getQuestionText(),
                        answer.getAnswer()
                );
                theoryscore += score;
                totalscore += score;
            }

            submissionRepository.save(submission);
        }

        attempt.setTotal_score(totalscore);
        attempt.setMcq_score(mcqscore);
        attempt.setCoding_score(codingscore);
        attemptRepository.save(attempt);

        return new TestResultDto(codingscore, mcqscore, theoryscore, totalscore);
    }

    private String getLoggedInUser(){
         return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public String sanitizeInput(String input) {

        if (input == null) return "";

        // remove labels
        input = input.replaceAll("nums = ", "");
        input = input.replaceAll("target = ", "");

        // convert to 2-line format
        input = input.replaceAll("],", "\n");

        // remove brackets
        input = input.replaceAll("\\[|\\]", "");

        // replace commas with space
        input = input.replaceAll(",", " ");

        // remove quotes
        input = input.replaceAll("\"", "");

        return input.trim();
    }
 public ResponseEntity<?> RunCode(long questionId , String code){


         Question question = questionRepository.findById(questionId)
                 .orElseThrow();


         List<Test_cases> testcases = question.getTestcases()
                 .stream()
                 .filter(tc -> tc.isHidden())
                 .toList();

         List<Map<String, Object>> results = new ArrayList<>();
          int index =1;
         for (Test_cases tc : testcases) {

             String cleanInput = sanitizeInput(tc.getInput());

             String output = judge0Service.runCode(code, cleanInput);

             System.out.println("RUN OUTPUT: " + output);

             Map<String, Object> res = new HashMap<>();
             res.put("testCase", index++);
             res.put("input", tc.getInput());
             res.put("expected", tc.getExpectedoutput());
             res.put("output", output);

             if (output == null) {
                 res.put("status", "ERROR");
             }


             else if (output.startsWith("COMPILE_ERROR") ||
                     output.startsWith("RUNTIME_ERROR")) {

                 res.put("status", "ERROR");
             }

             else if (output.equals("TLE")) {
                 res.put("status", "TLE");
             }


             else if (output.trim().equals(tc.getExpectedoutput().trim())) {
                 res.put("status", "PASSED");
             }


             else {
                 res.put("status", "FAILED");
             }

             results.add(res);
         }
     System.out.println("TOTAL TESTCASES: " + question.getTestcases().size());
         return ResponseEntity.ok(results);
     }
}







