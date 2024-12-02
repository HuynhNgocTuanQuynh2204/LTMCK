/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package remotedestopckltm;

/**
 *
 * @author TUẤN QUỲNH CODER
 */
public enum Commands {
    PRESS_MOUSE(-1),
    RELEASE_MOUSE(-2),
    PRESS_KEY(-3),  // Nhấn phím
    RELEASE_KEY(-4),  // Thả phím
    MOVE_MOUSE(-5);

    private final int abbrev;

    Commands(int abbrev) {
        this.abbrev = abbrev;
    }

    public int getAbbrev() {
        return abbrev;
    }
}

