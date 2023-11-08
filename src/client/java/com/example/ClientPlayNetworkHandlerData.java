package com.example;

/*
    Data for ClientPlayNetworkHandlerMixin and MinecraftClientMixin
 */
public class ClientPlayNetworkHandlerData {
    public static boolean disableInterrupt = false;
    public static boolean noInterrupt = false;
    public static double x;
    public static double y;

    public static void interrupt() {
        if (!disableInterrupt) noInterrupt = false;
    }
}
