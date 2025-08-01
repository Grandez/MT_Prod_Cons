package com.sdp.poc.threading.base.logging;

public enum Color {
     RESET  ("\033[0m"   )
    ,BOLD   ("\033[1m")
    ,BLACK  ("\033[0;30m")
    ,RED    ("\033[0;31m")
    ,GREEN  ("\033[0;32m")
    ,YELLOW ("\033[0;33m")
    ,BLUE   ("\033[0;34m")
    ,PURPLE ("\033[0;35m")
    ,CYAN   ("\033[0;36m")
    ,WHITE  ("\033[0;37m")


    ,BLACK_BOLD  ("\033[1;30m")
    ,RED_BOLD    ("\033[1;31m")
    ,GREEN_BOLD  ("\033[1;32m")
    ,YELLOW_BOLD ("\033[1;33m")
    ,BLUE_BOLD   ("\033[1;34m")
    ,PURPLE_BOLD ("\033[1;35m")
    ,CYAN_BOLD   ("\033[1;36m")
    ,WHITE_BOLD  ("\033[1;37m")
    ;

    private String color;
    Color(String color)      { this.color = color; }
    public String getColor() { return color; }
}
