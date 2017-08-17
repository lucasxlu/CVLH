package com.cvlh.spider;

import com.cvlh.entity.NlpJob;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * a web spider for http://www.nlpjob.com/jobs
 */
public class NlpJobSpider {
    private static final int TIME_OUT = 10000;

    public void crawlJobInfo(String jobType) throws IOException {
        String requestUrl = String.format("http://www.nlpjob.com/jobs/%s?p=1", jobType);
        System.out.println(requestUrl);
        Pattern regex = Pattern.compile("http://www.nlpjob.com/job/\\d");
        Document document = Jsoup.parse(new URL(requestUrl), NlpJobSpider.TIME_OUT);
        document.getElementById("job-listings").getElementsByAttributeValueContaining("class", "row").forEach(element -> {
            String href = element.getElementsByTag("a").attr("href").toString();
            String jobName = element.getElementsByTag("a").text().toString();
            NlpJob nlpJob = new NlpJob(jobName, null, null, 0, href, null);
            try {
                int applyNum = Integer.parseInt(Jsoup.parse(new URL(href), NlpJobSpider.TIME_OUT).getElementById("applied-to-job").text().replace("申请人", "").trim());
                nlpJob.setApplyNum(applyNum);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (element.getElementsByAttributeValue("class", "la").size() >= 2) {
                String company = element.getElementsByAttributeValue("class", "la").first().text();
                String location = element.getElementsByAttributeValue("class", "la").first().text();
                nlpJob.setCompany(company);
                nlpJob.setLocation(location);
//                System.out.println(element.select("/span[1]/text()[1]").text());
//                applied-to-job
            }
            String dateString = element.select("span.time-posted").text().trim();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
            if (dateString.length() > 0) {
                try {
                    nlpJob.setPublishDate(simpleDateFormat.parse(dateString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(nlpJob);
        });
    }

    public static void main(String[] args) {
        try {
            new NlpJobSpider().crawlJobInfo(NlpJobType.SHIXI.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

enum NlpJobType {
    SHIXI("shixi"), PARTTIME("parttime"), FULLTIME("fulltime");

    NlpJobType() {
    }

    private String type;

    NlpJobType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}