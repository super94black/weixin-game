package org.cet.service.impl;

import org.cet.dao.ClasstableMapper;
import org.cet.pojo.mysql.Classtable;
import org.cet.service.SpiderService;
import org.cet.util.JsonUtils;
import org.cet.util.Result;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2018/9/26 22:40
 * @Content 爬虫实现类
 */
@Service
public class SpiderServiceImpl implements SpiderService{

    @Autowired
    private ClasstableMapper classtableMapper;

    private ArrayList sprideFromJwzx(String stuNum) throws IOException {
        String url = "http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh=" + stuNum;
//        Map<String,String> map = new HashMap<>();
//        Connection con = Jsoup.connect(url);//获取请求连接
//      //浏览器可接受的MIME类型。
        //模拟浏览器
//      con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//      con.header("Accept-Encoding", "gzip, deflate");
//      con.header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//      con.header("Connection", "keep-alive");
//      con.header("Host", "jwzx.cqupt.congm.in");
//      con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
        Document doc = Jsoup.connect(url).get();
//        Document doc = con.get();
        ArrayList arrayList = new ArrayList();
        Element element =  doc.getElementById("kbStuTabs-table");
        if(element == null){
            return arrayList;
        }
        Element table = element.getElementsByTag("table").get(0);
        Elements elements = table.getElementsByTag("tr");
        if(elements == null || elements.size() == 0){
            return arrayList;
        }
        for (int i = 1; i < elements.size(); i++) {
            //去掉星期几的tr
            Elements tds = elements.get(i).getElementsByTag("td");

            for (int k = 1; k < tds.size(); k++) {
                //去掉间歇
                ArrayList info = new ArrayList();
                Element td = tds.get(k);

                int count = 0;
                int spanSize = 0;
                int startOne = 1;//040350 -算法分析与设计 记录开始数
                int startTwo = 2;//地点：2108 记录开始数
                int perLog = 4;//每一条记录相隔数
                List<TextNode> list = td.textNodes();
                Elements span = td.getElementsByTag("span");

                for (int j = 0; j < list.size(); j++) {
                    count++;
                    TextNode textNode = list.get(j);
                    //如果是 1 6 11行时按照空格分隔
                    if(j == startOne){
                        String[] numAndName = textNode.text().toString().split("-");
                        startOne += perLog;
                        for (String s:numAndName) {
                            info.add(s.trim());
                        }
                    }
                    //分割地点：3333这种
                    else if(j == startTwo){
                        startTwo += perLog;
                        String[] locationArr = textNode.text().toString().split("：");
                        info.add(locationArr[1].trim());
                    }//分割 唐述 必修 4.0学分这种
                    else{
                        info.add(textNode.text().toString().trim());
                    }
                    if(count % 4 == 0){
                        String[] str = span.get(spanSize).text().toString().split("\\s+");
                        if(str.length == 1){
                            String s = (String) info.get(count - 2);//count 代表原始一节课的条数 应该是四条为一节课记录
                            info.set(count - 2,s + "(" + str[0] + ")");
                            str = span.get(spanSize++).text().toString().split("\\s+");

                        }
                        for (String s:str) {
                            info.add(s.trim());
                        }
                        spanSize++;
                    }

                }
                arrayList.add(info);
            }
        }
        System.out.println(arrayList);
        return arrayList;
    }

    public Result getClassTableFromJwzx(String stuNum) throws IOException {
        ArrayList list = sprideFromJwzx(stuNum);
        ArrayList<Classtable> newList = new ArrayList();
        if(list == null || list.size() == 0){
            Result result = new Result();
            result.setStatus(400);
            result.setMsg("该学号不存在");
            return result;
        }
        for (int i = 0; i < list.size(); i++){
            String[] weeks = {"星期一","星期二","星期三","星期四","星期五","星期六","星期天"};
            ArrayList infoList = (ArrayList) list.get(i);
            if (infoList == null || infoList.size() == 0){
                continue;
            }
            String[] classTime = {"1、2节","3、4节","中午间歇","5、6节","7、8节","下午间歇","9、10节","11、12节"};
            int classCount = infoList.size() / 8;
            int week = i % 7;
            int jieshu = i / 7;
            for (int j = 0; j < classCount; j++) {
                Classtable table = new Classtable();
                table.setStuNum("11111");
                table.setWeek(weeks[week]);
                table.setTermTime("201701");
                table.setJieshu(classTime[jieshu]);
                table.setClassNum((String) infoList.get(8*j));
                table.setCourseNum((String) infoList.get(8*j + 1));
                table.setCourseName((String) infoList.get(8*j + 2));
                table.setLocation((String) infoList.get(8*j + 3));
                table.setClassTime((String) infoList.get(8*j + 4));
                table.setTeacher((String) infoList.get(8*j + 5));
                table.setClassType((String) infoList.get(8*j + 6));
                table.setCoursePoint((String) infoList.get(8*j + 7));
                table.setStatus(String.valueOf(1));
                newList.add(table);
            }
        }
//        for (Classtable classtable:newList) {
//            classtableMapper.insert(classtable);
//        }
        return Result.ok(newList);
    }


}

