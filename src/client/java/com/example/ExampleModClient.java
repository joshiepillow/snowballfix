package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

public class ExampleModClient implements ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("snowballfix");

	private static final ArrayList<InetAddress> addresses = new ArrayList<>(){{
		String[] ips = {"https://monumenta-8.playmonumenta.com",
				"https://monumenta-11.playmonumenta.com",
				"https://server.playmonumenta.com"};
		for (String ip : ips) {
			try {
				add(InetAddress.getByName(new URL(ip).getHost()));
			} catch (Exception e) {
				ExampleModClient.LOGGER.error("Could not access: {}", ip);
				ExampleModClient.LOGGER.error(e);
			}
		}
	}};

	public static boolean connectedToServer() {
		ServerInfo serverInfo = MinecraftClient.getInstance().getCurrentServerEntry();
		if (serverInfo == null) return false;
		try {
			return addresses.contains(InetAddress.getByName(new URL("https://"+serverInfo.address).getHost()));
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void onInitializeClient() {
		LOGGER.info("SnowballFix Loaded");
	}
}
