package com.skillbox.redisdemo;

import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;

import static java.lang.System.out;

public class Towns
{
    // Объект для работы с Redis
    private RedissonClient redisson;

    // Объект для работы с ключами
    private RKeys rKeys;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<String> towns;

    private final static String KEY = "TOWNS";

    public Towns(RedissonClient redisson) {
        this.redisson = redisson;
    }

    void init() {
        rKeys = redisson.getKeys();
        towns = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);

        addTown(10000.0, "Москва");
        addTown(5000.0, "Мурманск");
        addTown(4000.0, "Псков");
        addTown(6000.0, "Великий Новгород");
        addTown(7500.0, "Сочи");
        addTown(13000.0, "Сургут");
        addTown(25000.0, "Владивосток");
        addTown(11000.0, "Краснодар");
        addTown(20000.0, "Магадан");
        addTown(3000.0, "Петрозаводск");
        addTown(3500.0, "Архангельск");
    }

    void addTown(double price, String name)
    {
        //ZADD TOWNS
        towns.add(price, name);
    }

    void printThreeCheapestTickets()
    {
        out.println("\nТри самых дешевых билета:");
        String[] arr = towns.toArray( new String[towns.size()]);
        for (int i = 0; i < 3; i++)
            out.println(arr[i] + " " + towns.getScore(arr[i]));
    }

    void printThreeMostExpensiveTickets()
    {
        out.println("\nТри самых дорогих билета:");
        String[] arr = towns.toArray( new String[towns.size()]);
        for (int i = arr.length - 1; i > arr.length - 4; i--)
            out.println(arr[i] + " " + towns.getScore(arr[i]));
    }

    void shutdown()
    {
        redisson.shutdown();
    }
}