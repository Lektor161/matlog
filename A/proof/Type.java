package proof;

public enum Type {
    AX("Ax"),
    EIMPL  ("E->"),
    IIMPL  ("I->"),
    IAND   ("I&"),
    ELAND  ("El&"),
    ERAND  ("Er&"),
    ILOR   ("Il|"),
    IROR   ("Ir|"),
    EOR    ("E|"),
    EBOTTOM("E_|_");

    String name;
    Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
