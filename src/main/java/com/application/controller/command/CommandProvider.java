package com.application.controller.command;

import com.application.controller.command.implementation.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<CommandName, Command> repository = new HashMap<>();

    public CommandProvider() {
        repository.put(CommandName.MAIN, new MainPage());
        repository.put(CommandName.SHOW_SIGN_IN,new ShowSignIn());
        repository.put(CommandName.LOG_IN,new LogIn());
        repository.put(CommandName.SHOW_ERROR_PAGE,new ShowErrorPage());
        repository.put(CommandName.SHOW_SIGN_UP, new ShowSignUp());
        repository.put(CommandName.SHOW_CHECK_MAIL_PAGE, new ShowCheckMailPage());
        repository.put(CommandName.SIGN_UP, new SignUp());
        repository.put(CommandName.SHOW_CONTINUE_SIGN_UP, new ShowContinueSignUp());
        repository.put(CommandName.CONTINUE_SIGN_UP, new ContinueSignUp());
        repository.put(CommandName.LOG_OUT, new LogOut());
        repository.put(CommandName.ERROR_404, new Error404());
        repository.put(CommandName.SHOW_EDIT, new ShowEditPage());
        repository.put(CommandName.EDIT_PASSWORD, new EditPassword());
        repository.put(CommandName.SHOW_EDIT_PASSWORD, new ShowEditPassword());
        repository.put(CommandName.SHOW_SUCCESS_PASSWORD_PAGE, new ShowSuccessEditPassword());
        repository.put(CommandName.SHOW_BOOKING, new ShowBooking());
        repository.put(CommandName.CONTINUE_BOOKING, new ContinueBooking());
        repository.put(CommandName.SHOW_ROOMS, new ShowRooms());
        repository.put(CommandName.END_BOOKING, new EndBooking());
        repository.put(CommandName.WAIT_APPROVAL_BOOKING, new WaitApprovalBooking());
        repository.put(CommandName.SUCCESS_APPROVAL_BOOKING,new ShowSuccessApprovalBooking());
        repository.put(CommandName.APPROVAL_BOOKING, new ApprovalBooking());
        repository.put(CommandName.SHOW_PERSONAL_AREA, new ShowPersonalArea());
        repository.put(CommandName.SHOW_ADD_HOTEL, new ShowAddHotel());
        repository.put(CommandName.ADD_HOTEL, new AddHotel());
    }

    public Command getCommand(String name) {
        try {
            return repository.get(CommandName.valueOf(name));
        } catch (IllegalArgumentException e){
            return repository.get(CommandName.ERROR_404);
        }
    }
}
