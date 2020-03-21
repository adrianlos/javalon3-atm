package pl.sda.banking.atm;

class BadCredentialsException extends Exception {
    public BadCredentialsException(String message) {
        super(message);
    }
}
