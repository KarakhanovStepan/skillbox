package com.skillbox.redisdemo;

import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.System.out;

public class RedisStorage {

    // Объект для работы с Redis
    private RedissonClient redisson;

    // Объект для работы с ключами
    private RKeys rKeys;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<String> onlineUsers;

    private final static String KEY = "ONLINE_USERS";

    public RedisStorage(RedissonClient redisson) {
        this.redisson = redisson;
    }

    // Пример вывода всех ключей
    public void listKeys() {
        Iterable<String> keys = rKeys.getKeys();
        for(String key: keys) {
            out.println("KEY: " + key + ", type:" + rKeys.getType(key));
        }
    }

    void init() {
        rKeys = redisson.getKeys();
        onlineUsers = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);

        // Добавляем 20 пользователей сайта
        char ch = 'A';
        for(int i = 0; i < 20; i++)
        {
            onlineUsers.add(i + 1, ch++ + "адя");
        }
    }

    void printUsers()
    {
        Random random = new Random();
        String again = "";
        int numberOfLoops = 0;

        while (true) {
            List<String> arrayList = new ArrayList<>(Arrays.asList(onlineUsers.toArray(new String[onlineUsers.size()])));

            while(arrayList.size() > 0)
            {
                out.println("— На главной странице показываем пользователя " + arrayList.get(0)
                        + " " + onlineUsers.getScore(arrayList.get(0)) + again);
                arrayList.remove(arrayList.get(0));

                if (random.nextInt(10) == 5 && arrayList.size() > 0) {
                    int i = random.nextInt(arrayList.size());
                    out.println("> Пользователь " + onlineUsers.getScore(arrayList.get(i)) + " оплатил платную услугу");
                    out.println("— На главной странице показываем пользователя " + arrayList.get(i)
                            + " " + onlineUsers.getScore(arrayList.get(i)) + again);
                    arrayList.remove(arrayList.get(i));
                }

                try {
                    Thread.sleep(300);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if(numberOfLoops == 0)
                again += " Снова";
            else
                again += " и снова";

            numberOfLoops++;

            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    void shutdown() {
        redisson.shutdown();
    }
}