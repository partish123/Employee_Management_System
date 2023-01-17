package com.job.management.controllers;

import com.job.management.payload.JobRequestPayload;
import com.job.management.payload.MessageResponse;
import com.job.management.repository.JobRepository;
import com.job.management.services.JobService;
import com.job.management.utility.JobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/job/manage")
@CrossOrigin
public class JobController {
    @Autowired
    private JobService service;

    @Autowired
    private JobRepository jobRepository;

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);


    @PostMapping("/createJob")
    public ResponseEntity<String> createJob(@RequestBody JobRequestPayload payload) throws JobException {
        try {
            logger.info("Inside JobController createJob---{}",payload);
            MessageResponse m = service.createJob(payload);
            logger.info(m.getMessage());
            return new ResponseEntity<>("Job added successfully!", HttpStatus.CREATED);
        }catch (Exception e){
            throw new JobException("Sorry something went wrong in create job ",e);
        }
    }


    @PutMapping("/updateJob/{id}")
    public ResponseEntity<String> updateJob(@RequestBody JobRequestPayload payload, @PathVariable String id) throws JobException {
        try {
            logger.info("Inside JobController updateJob---{}",payload);
            MessageResponse m = service.updateJob(payload,Long.parseLong(id));
            logger.info(m.getMessage());
            return new ResponseEntity<>("Job updated successfully!", HttpStatus.OK);
        }catch (Exception e){
            throw new JobException("Sorry something went wrong in update job ",e);
        }
    }

    @DeleteMapping("/deleteJob/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable String id) throws JobException {
        try {
            logger.info("Inside JobController deleteJob---");
            MessageResponse m = service.deleteJob(Long.parseLong(id));
            logger.info("Message---{}",m);
            return new ResponseEntity<>("Job deleted successfully!",HttpStatus.OK);
        }catch (Exception e){
            throw new JobException("Sorry something went wrong in delete employee",e);
        }
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllJobs() throws JobException {
        try {
            logger.info("Inside JobController getAllJobs---");
            Object jobList = service.getAllJobs();
            logger.info("jobList----{}",jobList);
            if(jobList!=null)
                return new ResponseEntity<>(jobList,HttpStatus.OK);
            else
                return ResponseEntity.status(500).body("Failed to retrieve list of jobs!");
        }catch (Exception e){
            throw new JobException("Sorry something went wrong in getAllJobs",e);
        }
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<?> getJob(@PathVariable String id) throws JobException {
        try {
            logger.info("Inside JobController getJob---");
            Object employee = service.getJob(Long.parseLong(id));
            logger.info("job----{}",employee);
            if(employee!=null)
                return new ResponseEntity<>(employee,HttpStatus.OK);
            else
                return ResponseEntity.status(500).body("Failed to retrieve job!");
        }catch (Exception e){
            throw new JobException("Sorry something went wrong in getJob ",e);
        }
    }




//    @GetMapping("/search")
//    public ResponseEntity<?> searchBooks(@RequestParam(required = false ,defaultValue = "") String title, @RequestParam(required = false,defaultValue = "")
//    String author, @RequestParam(defaultValue ="") String price, @RequestParam(required = false,defaultValue = "") String publisher,@RequestParam(name="category" ,defaultValue="") String category) throws BookException {
//      try{
//          logger.info("Inside search book method");
//
//          List<Book> books =service.searchBook(title,author,price,publisher,category);
//
//          if(books.isEmpty()){
//              return new ResponseEntity<>("Books not found", HttpStatus.OK);
//          }
//
//          return new ResponseEntity<>(books, HttpStatus.OK);
//      }
//      catch (Exception e){
//          throw new BookException("Sorry something went wrong in search book functionality",e);
//      }
//
//    }
//
//        @PostMapping("/author/{author-id}/books")
//        public ResponseEntity<String> createBook(@Valid @PathVariable("author-id") int authorId, @RequestBody CreateBookRequest payload) throws BookException {
//            try {
//                LocalDate date = LocalDate.now();
//                Book book = new Book(payload.getBookTitle(),authorId, payload.getCategory(), payload.getPrice(), payload.getPublisher(), date, date, payload.isActive(), payload.getBookcontent(),0L);
//                service.createbook(book);
//                return new ResponseEntity<>("Book added successfully!",HttpStatus.CREATED);
//            }catch (Exception e){
//                throw new BookException("Sorry something went wrong in create book functionality",e);
//            }
//        }
//
//    @PostMapping("/author/{author-id}/books/{book-id}")
//    public ResponseEntity<String> updateBook(@Valid @PathVariable("author-id") int authorId, @Valid @PathVariable("book-id") int bookId,@RequestBody UpdateBookRequest payload) throws BookException {
//        try{
//            LocalDate date = LocalDate.now();
//            Book book = new Book(bookId,payload.getBookTitle(),authorId, payload.getCategory(), payload.getPrice(), payload.getPublisher(), date, date, payload.isActive(), payload.getBookcontent());
//            logger.debug("Book value is -> {}",book);
//            service.updateBook(book , authorId , bookId);
//            return new ResponseEntity<>("Book is updated successfully",HttpStatus.ACCEPTED);
//        }
//        catch (Exception e){
//            throw new BookException("Either GIVEN AUTHOR_ID NOT MATCHING WITH ACTUAL AUTHOR or Book id is not found in repository",e);
//        }
//
//
//    }
//
//
//    @PostMapping("author/{authorId}/books/{bookId}/block={block}")
//    public ResponseEntity<String> blockOrUnBlockBook(@PathVariable(value="authorId") String authorId,@PathVariable(value="bookId") String bookId,@PathVariable(value = "block") String block) throws BookException {
//        if(block!=null && !block.isEmpty() && authorId!=null && bookId!=null) {
//            try {
//                boolean status = service.blockOrUnBlockBookByAuthor(authorId,bookId,block);
//                if(status) {
//                    if(block.equalsIgnoreCase("Yes"))
//                        return ResponseEntity.status(200).body("SUCCESSFULLY BLOCKED");
//                    else
//                        return ResponseEntity.status(200).body("SUCCESSFULLY UNBLOCKED");
//                }
//                else {
//                    throw new BookException("Book ID / AUTHOR ID NOT MATCHING WITH RECORDS");
//                }
//            }catch(Exception e) {
//                throw new BookException("Book ID / AUTHOR ID NOT MATCHING WITH RECORDS",e);
//            }
//        }
//        else {
//            return ResponseEntity.status(400).body("Missing data for authorId:"+ authorId +" bookId: "+bookId+" block: "+block);
//        }
//    }
//
//
//    @PostMapping("/{bookId}/subscribe")
//    public  ResponseEntity<String> subscribeBook(@RequestBody Reader reader, @PathVariable String bookId) throws BookException{
//        try {
//            boolean status= service.subscribeBook(bookId,reader);
//            if(status)
//                return ResponseEntity.status(200).body("SUBSCRIBED SUCCESSFULLY");
//            else
//                throw new BookException("BOOK_IS_ALREADY_SUBSCRIBED");
//
//        }catch(Exception e) {
//            throw new BookException("BOOK_IS_ALREADY_SUBSCRIBED",e);
//        }
//    }
//
//
//    @GetMapping("/readers/{emailId}/books")
//    public ResponseEntity<?> getAllSubscribedBooks(@Valid @PathVariable String emailId) throws BookException{
//        if(emailId!=null && !emailId.isEmpty()) {
//            try {
//                List<Book> subscribedBooks=service.getAllSubscribedBooks(emailId);
//                if(subscribedBooks!=null && !subscribedBooks.isEmpty()) {
//                    return ResponseEntity.status(200).body(subscribedBooks);
//                }
//                else{
//                    return null;
//                }
//            }catch(Exception e) {
//                throw new BookException("Sorry something went wrong..Please try after some time",e);
//            }
//        }
//       else {
//            throw new BookException("DATA_MISSING");
//        }
//
//    }
//
//
//    @GetMapping("/readers/{emailId}/books/{subscriptionId}")
//    public ResponseEntity<Book> getSubscribedBook(@PathVariable String emailId,@PathVariable String subscriptionId) throws BookException{
//        if(emailId!=null&&!emailId.isEmpty()) {
//            try {
//                Book subscribedBook = service.getSubscribedBook(emailId,subscriptionId);
//                return new ResponseEntity<>(subscribedBook,HttpStatus.OK);
//            }catch(Exception e) {
//                throw new BookException("Sorry something went wrong",e);
//            }
//        }
//        else {
//            throw new BookException("Parameters are missing");
//        }
//    }
//
//    @GetMapping("/readers/{emailId}/books/{subscriptionId}/read")
//    public ResponseEntity<String> getSubscribedBookContent(@PathVariable String emailId,@PathVariable String subscriptionId) throws BookException{
//        if(emailId!=null&&!emailId.isEmpty()) {
//            try {
//                Book subscribedBook= service.getSubscribedBook(emailId,subscriptionId);
//                return new ResponseEntity<>(subscribedBook.getContent(),HttpStatus.OK);
//            }catch(Exception e) {
//                throw new BookException("Sorry something went wrong",e);
//            }
//        }
//        else {
//            throw new BookException("Parameters are missing");
//        }
//    }
//
//
//    @PostMapping("/readers/{emailId}/books/{subscriptionId}/cancel-subscription")
//    public ResponseEntity<String>cancelSubscriptionWithIn24Hours(@PathVariable String emailId,@PathVariable String subscriptionId ) throws BookException{
//        if(emailId!=null &&!emailId.isEmpty() && !subscriptionId.isEmpty() && subscriptionId!=null) {
//            boolean cancel = service.cancleSubscriptionWithIn24Hours(emailId,subscriptionId);
//            if(cancel) {
//                return ResponseEntity.status(200).body("SUBSCRIPTION CANCELED SUCCESSFULLY");
//            }
//            else {
//                throw new BookException("SOMETHING WENT WRONG PLEASE TRY AFTER SOME TIME");
//            }
//        }
//        else {
//            return ResponseEntity.status(400).body("DATA_MISSING");
//        }
//    }

}
