package com.example.expensesharing;

import com.example.expensesharing.controllers.ExpenseController;
import com.example.expensesharing.controllers.GroupController;
import com.example.expensesharing.controllers.UserController;
import com.example.expensesharing.dtos.*;
import com.example.expensesharing.models.*;
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
		createGroupRequestDto.setAdminEmail("meerajoshi@gmail.com");

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

	@Test
	void testAddMember(){
		AddRemoveMemberGroupRequestDto requestDto = new AddRemoveMemberGroupRequestDto();

		requestDto.setUserEmail("rishijain@gmail.com");
		requestDto.setGroupName("Roommates");

		AddRemoveMemberGroupResponseDto responseDto = groupController.addMember(requestDto);

		if(responseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			System.out.println("Member is added successfully.");
			List<User> members = responseDto.getMembers();
			System.out.println("New count of members = "+ members.size());
		}
		else{
			System.out.println("Member is not added.");
		}
	}

	@Test
	void testRemoveMember(){
		AddRemoveMemberGroupRequestDto requestDto = new AddRemoveMemberGroupRequestDto();

		requestDto.setUserEmail("rishijain@gmail.com");
		requestDto.setGroupName("Roommates");

		AddRemoveMemberGroupResponseDto responseDto = groupController.removeMember(requestDto);

		if(responseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			System.out.println("Member is removed successfully.");
			List<User> members = responseDto.getMembers();
			System.out.println("New count of members = "+ members.size());

		}
		else{
			System.out.println("Member is not removed.");
		}
	}

	@Test
	void testGetMembers(){

		AddRemoveMemberGroupRequestDto requestDto = new AddRemoveMemberGroupRequestDto();
		requestDto.setGroupName("Roommates");

		AddRemoveMemberGroupResponseDto responseDto = groupController.getAllMembers(requestDto);

		if(responseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			List<User> members = responseDto.getMembers();
			for(User user: members){
				System.out.println(user.getName());
			}

		}
		else{
			System.out.println("Get all members failed.");
		}
	}

	@Test
	void testGetAllGroups(){

		GetGroupsRequestDto requestDto = new GetGroupsRequestDto();

		requestDto.setUserEmail("parthvyas24@gmail.com");
		GetGroupsResponseDto responseDto = groupController.getAllGroups(requestDto);

		if(responseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
			List<Group> groups = responseDto.getGroups();
			for(Group group: groups){
				System.out.println(group.getName());
			}
		}
		else{
			System.out.println("Get all groups failed.");
		}
	}
}
