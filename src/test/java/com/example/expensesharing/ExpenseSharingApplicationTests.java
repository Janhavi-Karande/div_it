package com.example.expensesharing;

import com.example.expensesharing.controllers.ExpenseController;
import com.example.expensesharing.controllers.GroupController;
import com.example.expensesharing.controllers.UserController;
import com.example.expensesharing.dtos.*;
import com.example.expensesharing.models.Expense;
import com.example.expensesharing.models.ExpenseType;
import com.example.expensesharing.models.User;
import com.example.expensesharing.models.UserExpenseType;
import com.example.expensesharing.services.UserService;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ExpenseSharingApplicationTests {

	@Autowired
	private UserController userController;
	@Autowired
	private GroupController groupController;
	@Autowired
	private ExpenseController expenseController;

	@Test
	void contextLoads() {
	}

	@Test
	void userSingUpTest(){
		UserSignUpRequestDto signupRequestDto = new UserSignUpRequestDto();
		signupRequestDto.setEmail("rohinipillai@gmail.com");
		//signupRequestDto.setPassword("Parthvyas*14");
		//signupRequestDto.setPassword("Rishijain35");
		signupRequestDto.setPassword("Rohinipillai93");
		signupRequestDto.setName("Rohini Pillai");
		signupRequestDto.setPhone("7510349870");

		UserSignUpResponseDto signupResponseDto = userController.signup(signupRequestDto);

		if(signupResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS))
			System.out.println("Signup is successful.");
		else
			System.out.println("Signup is failed.");
	}

	@Test
	void userLoginTest(){
		UserLoginRequestDto loginRequestDto = new UserLoginRequestDto();

		loginRequestDto.setEmail("rishijain@gmail.com");
		loginRequestDto.setPassword("Rishijain35");

		UserLoginResponseDto loginResponseDto = userController.login(loginRequestDto);

		if(loginResponseDto.getStatus().equals(ResponseStatus.SUCCESS))
			System.out.println("Login is successful.");

		else
			System.out.println("Login is failed.");
	}

	@Test
	void userUpdateTest(){
		UpdateUserDetailsRequestDto updateUserDetailsRequestDto = new UpdateUserDetailsRequestDto();

		updateUserDetailsRequestDto.setEmail("meerajoshi@gmail.com");
		updateUserDetailsRequestDto.setPassword("Meerajoshi#20");

		UpdateUserDetailsResponseDto updateUserDetailsResponseDto = userController.updateUserPassword(updateUserDetailsRequestDto);

		if(updateUserDetailsResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			System.out.println("Update user password is successful.");
			System.out.println("User password:" +updateUserDetailsResponseDto.getUser().getPassword());
		}
		else{
			System.out.println("Update user password is failed.");
		}

		updateUserDetailsRequestDto.setPhone("9410375682");
		updateUserDetailsResponseDto = userController.updateUserPhone(updateUserDetailsRequestDto);

		if(updateUserDetailsResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			System.out.println("Update user phone number is successful.");
			System.out.println("User phone number:" +updateUserDetailsResponseDto.getUser().getPhone());
		}
		else{
			System.out.println("Update user phone number is failed.");
		}

	}

	@Test
	void testCreateGroup(){
		CreateGroupRequestDto createGroupRequestDto = new CreateGroupRequestDto();
		createGroupRequestDto.setGroupName("Roommates");
		User user = new User();
		user.setEmail("meerajoshi@gmail.com");
		createGroupRequestDto.setAdmin(user);

		List<User> members = new ArrayList<>();
		User userMember1 = new User();
		userMember1.setEmail("rohinipillai@gmail.com");

		User userMember2 = new User();
		userMember2.setEmail("parthvyas24@gmail.com");

		members.add(userMember1);
		members.add(userMember2);

		createGroupRequestDto.setMembers(members);

		CreateGroupResponseDto createGroupResponseDto = groupController.createGroup(createGroupRequestDto);

		if(createGroupResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			System.out.println("Group is created successfully.");
			System.out.println("Group: " +createGroupResponseDto.getGroup().getName()+ ", "
			+createGroupResponseDto.getGroup().getMembers()+ ", " + createGroupResponseDto.getGroup().getAdmin().getName());
		}
		else{
			System.out.println("Group is not created.");
		}
	}

	@Test
	void testCreateExpense(){
		CreateExpenseRequestDto createExpenseRequestDto = new CreateExpenseRequestDto();

		createExpenseRequestDto.setName("Rent");
		createExpenseRequestDto.setDescription("Rent expense");
		createExpenseRequestDto.setCreatedByUserEmail("parthvyas24@gmail.com");
		createExpenseRequestDto.setGroupName("Roommates");
		createExpenseRequestDto.setTotalAmount(9000.0);

		List<UserExpenseHelper> helpers = new ArrayList<>();
		UserExpenseHelper helperMeera = new UserExpenseHelper("meerajoshi@gmail.com", UserExpenseType.HAD_TO_PAY, 3000.0);
		UserExpenseHelper helperRohini = new UserExpenseHelper("rohinipillai@gmail.com", UserExpenseType.PAID_BY, 6000.0);
		UserExpenseHelper helperParth = new UserExpenseHelper("parthvyas24@gmail.com", UserExpenseType.HAD_TO_PAY, 3000.0);

		helpers.add(helperMeera);
		helpers.add(helperRohini);
		helpers.add(helperParth);

		createExpenseRequestDto.setUserExpenseHelpers(helpers);

		CreateExpenseResponseDto createExpenseResponseDto = expenseController.createExpense(createExpenseRequestDto);

		if(createExpenseResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			System.out.println("Expense is created successfully. " + createExpenseResponseDto.getExpense().getName());
		}
		else{
			System.out.println("Expense is not created.");
		}
	}

	@Test
	void testSettleUp(){
		SettleUpGroupRequestDto settleUpGroupRequestDto = new SettleUpGroupRequestDto();
		settleUpGroupRequestDto.setGroupName("Roommates");
		settleUpGroupRequestDto.setUserId(4);

		// 2 - Meera
		// 4- Parth
		// 5 - Rohini
		SettleUpGroupResponseDto settleUpGroupResponseDto = expenseController.settleUp(settleUpGroupRequestDto);

		if(settleUpGroupResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			System.out.println("Settle up is successful. ");
			for(Expense expense: settleUpGroupResponseDto.getSettledExpenses()){
				System.out.println(expense.getDescription()+ " ");
			}

		}
		else{
			System.out.println("Settle up is failed.");

		}
	}

	@Test
	void testGetAllTransactions(){
		TransactionsInGroupRequestDto transactionsInGroupRequestDto = new TransactionsInGroupRequestDto();
		transactionsInGroupRequestDto.setGroupName("Roommates");

		TransactionsInGroupResponseDto transactionsInGroupResponseDto = expenseController.getAllTransactions(transactionsInGroupRequestDto);

		if(transactionsInGroupResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			System.out.println("All transactions is successful.");
			for(Expense expense: transactionsInGroupResponseDto.getExpenses())
				System.out.println(expense.getDescription()+ " ");
		}
		else{
			System.out.println("All transactions is not successful.");
		}
	}

	@Test
	void testGetExpenseHistory(){
		ExpenseHistoryRequestDto expenseHistoryRequestDto = new ExpenseHistoryRequestDto();
		expenseHistoryRequestDto.setGroupName("Roommates");

		ExpenseHistoryResponseDto expenseHistoryResponseDto = expenseController.getExpenseHistory(expenseHistoryRequestDto);

		if(expenseHistoryResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			System.out.println("Expense history is successful.");
			for(Expense expense: expenseHistoryResponseDto.getExpenses()){
				if(expense.getExpenseType().equals(ExpenseType.REAL)){
					System.out.println(expense.getName()+ " - Not settle" );
				}
				else if (expense.getExpenseType().equals(ExpenseType.SETTLED)) {
					System.out.println(expense.getName()+ " - Settled" );

				}
			}
		}
		else{
			System.out.println("Expense history is not successful.");
		}
	}
}
