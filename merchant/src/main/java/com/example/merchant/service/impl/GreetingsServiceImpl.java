package com.example.merchant.service.impl;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.GreetingsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class GreetingsServiceImpl implements GreetingsService {

    private static List<String> morningList = null;
    private static List<String> forenoonList = null;
    private static List<String> nooningList = null;
    private static List<String> afternoonList = null;
    private static List<String> eveningList = null;

    static {
        if (morningList == null) {
            morningList = new ArrayList<>();
            morningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            morningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            morningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            morningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            morningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            morningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            morningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            morningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            morningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            morningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
        }
        if (forenoonList == null) {
            forenoonList = new ArrayList<>();
            forenoonList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            forenoonList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            forenoonList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            forenoonList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            forenoonList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            forenoonList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            forenoonList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            forenoonList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            forenoonList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            forenoonList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
        }
        if (nooningList == null) {
            nooningList = new ArrayList<>();
            nooningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            nooningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            nooningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            nooningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            nooningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            nooningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            nooningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            nooningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            nooningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            nooningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
        }
        if (afternoonList == null) {
            afternoonList = new ArrayList<>();
            afternoonList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            afternoonList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            afternoonList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            afternoonList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            afternoonList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            afternoonList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            afternoonList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            afternoonList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            afternoonList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            afternoonList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
        }
        if (eveningList == null) {
            eveningList = new ArrayList<>();
            eveningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            eveningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            eveningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            eveningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            eveningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            eveningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            eveningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            eveningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
            eveningList.add("现在过的每一天，都是我们余下生命中最年轻的一天。愿你珍惜现在，早安!");
            eveningList.add("诮悄的打开快乐的大门，欢迎你进来，甜蜜的爱情，充实的工作，美好的一天从这一刻开始!早安，朋友!");
        }
    }

    @Override
    public ReturnJson getGreetings() {
        int i = new Random().nextInt(10);
        Date date = new Date();
        int hours = date.getHours();
        if (hours <= 8 && hours > 4) {
            return ReturnJson.success("", morningList.get(i));
        } else if (hours <= 11) {
            return ReturnJson.success("", forenoonList.get(i));
        } else if (hours <= 13) {
            return ReturnJson.success("", nooningList.get(i));
        } else if (hours <= 18) {
            return ReturnJson.success("", afternoonList.get(i));
        } else {
            return ReturnJson.success("", eveningList.get(i));
        }
    }

}
