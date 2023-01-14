package com.employee.management.controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.payload.request.CreateEmployeePayload;
import com.employee.management.payload.request.UpdateEmployeePayload;
import com.employee.management.payload.response.MessageResponse;
import com.employee.management.services.EmployeeService;
import com.employee.management.utility.EmployeeException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee/manage")
public class EmployeeController {
	@Autowired
	EmployeeService service;
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	
//	@GetMapping("/user")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	public String userAccess() {
//		return "User Content.";
//	}
	
    @PostMapping("/createEmployee")
    public ResponseEntity<String> createEmployee(@RequestBody CreateEmployeePayload payload) throws EmployeeException {
        try {
            MessageResponse m = service.createEmployee(payload);
            return new ResponseEntity<>("Employee added successfully!",HttpStatus.CREATED);
        }catch (Exception e){
            throw new EmployeeException("Sorry something went wrong in create ",e);
        }
    }
    
    
    @PostMapping("/updateEmployee/{id}")
    public ResponseEntity<String> createEmployee(@RequestBody UpdateEmployeePayload payload, @PathVariable Long id) throws EmployeeException {
        try {
            MessageResponse m = service.updateEmployee(payload,id);
            
            return new ResponseEntity<>("Employee added successfully!",HttpStatus.CREATED);
        }catch (Exception e){
            throw new EmployeeException("Sorry something went wrong in create ",e);
        }
    }
    
    @DeleteMapping("/deleteEmployee/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) throws EmployeeException {
        try {
            MessageResponse m = service.deleteEmployee(id);
            return new ResponseEntity<>("Employee added successfully!",HttpStatus.CREATED);
        }catch (Exception e){
            throw new EmployeeException("Sorry something went wrong in create ",e);
        }
    }
	
    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees() throws EmployeeException {
        try {
        	Object employeeList = service.getAllEmployees();
            if(employeeList!=null)
            return new ResponseEntity<>(employeeList,HttpStatus.OK);
            else
            	return ResponseEntity.status(500).body("Failed to retrieve list of employees!");
        }catch (Exception e){
            throw new EmployeeException("Sorry something went wrong in create ",e);
        }
    }
    
    @GetMapping("/getEmployee/{empId}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) throws EmployeeException {
        try {
        	Object employee = service.getEmployee(id);
            if(employee!=null)
            return new ResponseEntity<>(employee,HttpStatus.OK);
            else
            	return ResponseEntity.status(500).body("Failed to retrieve employee!");
        }catch (Exception e){
            throw new EmployeeException("Sorry something went wrong in create ",e);
        }
    }
    
    
	
	
	
 
    
//	@PostMapping("/author/{authorId}/books")
//	//	@PreAuthorize("hasRole('AUTHOR')")
//	public ResponseEntity<?> createBook(@RequestBody CreateBookRequest book, @PathVariable String authorId) throws UserException {
//		try {
//			Object createBook = userService.createBook(book, authorId);
//			if (createBook != null)
//				return ResponseEntity.status(200).body(createBook);
//			else
//				throw new UserException("Book is not added--->");
//		} catch (Exception e) {
//			throw new UserException("Book is not added---> error", e);
//		}
//
//	}
//
//	@PostMapping(value="/author/{authorId}/books/{bookId}")
//	public ResponseEntity<?> updateBook(@RequestBody UpdateBookRequest book, @PathVariable String authorId, @PathVariable String bookId) throws UserException{
//
//		try {
//			Object updateBook= userService.updateBook(book,authorId,bookId);
//			if (updateBook != null)
//				return ResponseEntity.status(200).body(updateBook);
//			else
//				throw new UserException("Book not updated--->");
//
//		}catch(Exception e) {
//			throw new UserException("Book is not updated..got error--->",e);
//		}
//	}
//
//	@GetMapping("/search")
//	public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false ,defaultValue = "") String title, @RequestParam(required = false,defaultValue = "")
//	String author, @RequestParam(defaultValue ="") String price, @RequestParam(required = false,defaultValue = "") String publisher,@RequestParam(name="category" ,defaultValue="") String category) throws UserException{
//		List<Book> books=null;
//		try {
//			books= userService.searchBook(category,title,author,price,publisher);
//			return ResponseEntity.status(200).body(books);
//
//		}catch(Exception e) {
//			throw new UserException("Search ERROR()--->",e);
//		}
//	}
//
//
//	@PostMapping("/{bookId}/subscribe")
//	public  ResponseEntity<String> subscribeBook(@RequestBody Reader reader, @PathVariable String bookId) throws UserException{
//		try {
//			boolean sub= userService.subscribeBook(bookId,reader);
//			if(sub)
//				return ResponseEntity.status(200).body("SUCCESSFULLY SUBSCRIBED");
//			else
//				throw new UserException("NOT SUBSCRIBED");
//
//		}catch(Exception e) {
//			throw new UserException("NOT SUBSCRIBED",e);
//		}
//	}
//
//
//	@GetMapping("/readers/{emailId}/books")
//	public ResponseEntity<?> getAllSubscribeBooksByReader(@PathVariable String emailId) throws UserException{
//		if(emailId!=null&&!emailId.equalsIgnoreCase("")) {
//			try {
//				Object subscribBooksByReader=userService.getAllSubscribeBooksByReader(emailId);
//				if(subscribBooksByReader!=null) {
//					return ResponseEntity.status(200).body(subscribBooksByReader);
//				}
//				else{
//					return ResponseEntity.status(200).body("No books are subscribed by reader");
//				}
//			}catch(Exception e) {
//				throw new UserException("SOMETHING WENT WRONG PLEASE TRY AFTER SOME TIME");
//			}
//		}
//		else {
//			throw new UserException("DATA MISSING");
//		}
//	}
//
//
//	@GetMapping("/readers/{emailId}/books/{subscriptionId}")
//	public ResponseEntity<Book> getSubscribeBookByReaderEmailId(@PathVariable String emailId,@PathVariable String subscriptionId) throws UserException{
//		if(emailId!=null&&!emailId.equalsIgnoreCase("")) {
//			try {
//				Book subscribBookByReader=userService.getSubscribeBookByReaderEmailId(emailId,subscriptionId);
//				return ResponseEntity.status(200).body(subscribBookByReader);
//			}catch(Exception e) {
//				throw new UserException("SOMETHING WENT WRONG PLEASE TRY AFTER SOME TIME",e);
//			}
//		}
//		else {
//			throw new UserException("DATA_MISSING");
//		}
//	}
//
//
//	@GetMapping("/readers/{emailId}/books/{subscriptionId}/read")
//	public ResponseEntity<String> getSubscribeBookContentByReaderEmailId(@PathVariable String emailId,@PathVariable String subscriptionId) throws UserException{
//		if(emailId!=null&&!emailId.equalsIgnoreCase("")) {
//			try {
//				String bookContent= userService.getSubscribeBookByReader(emailId,subscriptionId);
//				return ResponseEntity.status(200).body(bookContent);
//			}catch(Exception e) {
//				throw new UserException("SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME");
//			}
//		}
//		else {
//			throw new UserException("DATA_MISSING");
//		}
//	}
//
//
//	@PostMapping("/readers/{readerId}/books/{subscriptionId}/cancel-subscription")
//	public ResponseEntity<String>cancleSubscriptionWithIn24Hours(@PathVariable String readerId,@PathVariable String subscriptionId ) throws UserException{
//		if(readerId!=null &&!readerId.equalsIgnoreCase("") && subscriptionId!=null && !subscriptionId.equalsIgnoreCase("")) {
//			boolean cancle=userService.cancleSubscriptionWithIn24Hours(readerId,subscriptionId);
//			if(cancle) {
//				return ResponseEntity.status(200).body("SUBSCRIPTION_CANCELED_SUCCESSFULLY");
//			}
//			else {
//				throw new UserException("SOMETHING_WENT_WRONG_PLEASE_TRY_AFTER_SOME_TIME");
//			}
//		}
//		else {
//			return ResponseEntity.status(400).body("DATA_MISSING");
//		}
//	}
//
//	@PostMapping("/author/{authorId}/books/{bookId}/block={block}")
//	public ResponseEntity<?> blockOrUnBlockBookByAuthor(@PathVariable(value="authorId") String authorId,@PathVariable(value="bookId") String bookId,@PathVariable(value = "block") String block) throws UserException{
//		if(block!=null&&!block.equalsIgnoreCase("")&&authorId!=null&&bookId!=null) {
//			try {
//				boolean update=userService.blockOrUnBlockBookByAuthor(authorId,bookId,block);
//				if(update) {
//					if(block.equalsIgnoreCase("Yes"))
//						return ResponseEntity.status(200).body("blocked");
//					else
//						return ResponseEntity.status(200).body("unblocked");
//				}
//				else {
//					throw new UserException("SOMETHING WENT WRONG PLEASE_TRY_AFTER_SOME_TIME");
//				}
//			}catch(Exception e) {
//				throw new UserException("SOMETHING_WENT_WRONG_PLEASE_TRY_AFTER_SOME_TIME",e);
//			}
//		}
//		else {
//			return ResponseEntity.status(400).body("Data Missing!.. authorId:"+authorId+"bookId:"+bookId+"block:"+block);
//		}
//	}




}
