package pl.tomwodz.lottogame.domain.validator;

public class ValidatorConfiguration {
    public ValidatorFacade validatorFacade(){
        ValidatorNumber validatorNumber = new ValidatorNumber();
        ValidatorWinningNumbers validatorWinningNumbers = new ValidatorWinningNumbers();
        return new ValidatorFacade(validatorNumber, validatorWinningNumbers);
    }
}
