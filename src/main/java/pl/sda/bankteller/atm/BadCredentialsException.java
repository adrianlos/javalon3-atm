package pl.sda.bankteller.atm;

class BadCredentialsException extends Exception {
    public BadCredentialsException(String message) {
        super(message);
    }
}
