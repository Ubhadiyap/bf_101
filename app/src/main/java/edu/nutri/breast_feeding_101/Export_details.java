package edu.nutri.breast_feeding_101;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.sym.Name;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by Akano on 4/5/2017.
 */

public class Export_details extends Activity {

    MenuItem closeOption;
    LinearLayout course1_layout;
    String query, users;

    ArrayList<String> singleList;
    ArrayList<ArrayList<String>> listOfLists;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_details);

        singleList = new ArrayList<String>();
        listOfLists = new ArrayList<ArrayList<String>>();

        users = "kola";
        Bundle b = getIntent().getExtras();

        query = b.getString("query");

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        course1_layout = (LinearLayout)findViewById(R.id.course1_layout);

        if (query.equals("Users")){
            clear_layout();
        }
        if (query.equals("Course_Stats") || query.equals("Course_Scores")){
            for (int x=1;x<=6;x++){
                load_details(x, query);
            }
        }
    }

    private void clear_layout() {

        TextView textView38 = (TextView)findViewById(R.id.textView38);
        TextView textView39 = (TextView)findViewById(R.id.textView39);
        TextView textView40 = (TextView)findViewById(R.id.textView40);
        TextView textView41 = (TextView)findViewById(R.id.textView41);
        TextView textView42 = (TextView)findViewById(R.id.textView42);

        LinearLayout course2_layout = (LinearLayout)findViewById(R.id.course2_layout);
        LinearLayout course3_layout = (LinearLayout)findViewById(R.id.course3_layout);
        LinearLayout course4_layout = (LinearLayout)findViewById(R.id.course4_layout);
        LinearLayout course5_layout = (LinearLayout)findViewById(R.id.course5_layout);
        LinearLayout course6_layout = (LinearLayout)findViewById(R.id.course6_layout);

        RelativeLayout main = (RelativeLayout)findViewById(R.id.main);

        main.removeView(course2_layout);
        main.removeView(course3_layout);
        main.removeView(course4_layout);
        main.removeView(course5_layout);
        main.removeView(course6_layout);

        main.removeView(textView38);
        main.removeView(textView39);
        main.removeView(textView40);
        main.removeView(textView41);
        main.removeView(textView42);

        TextView textView37 = (TextView)findViewById(R.id.textView37);
        textView37.setText("Users");
        load_user_details();
    }

    private void load_user_details() {
        String score_url = UserDetails.database_url + "Users";
        Firebase Score_reference = new Firebase(score_url);

        Score_reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);

                String Age = map.get("Age").toString();
                String Email = map.get("Email").toString();
                String Gender = map.get("Gender").toString();
                String Level_of_Education = map.get("Level_of_Education").toString();
                String Marital_Status = map.get("Marital_Status").toString();
                String Name = map.get("Name").toString();
                String No_of_Children = map.get("No_of_Children").toString();
                String Pregnancy_Status = map.get("Pregnancy_Status").toString();
                String Religion = map.get("Religion").toString();
                String Time = map.get("Time").toString();
                String User_id = map.get("User_id").toString();

                String line = "______________________________________";

                users = users+ "Email: "+Email+"\n"+"User_id: "+User_id+"\n"+"Name: "+Name+"\n"+"Age: "+Age+"\n"+"Gender: "+Gender+"\n"+"Level of Education: "+Level_of_Education+"\n"+"Marital Status: "+Marital_Status+"\n"+"No of Children: "+No_of_Children+"\n"+"Pregnancy Status: "+Pregnancy_Status+"\n"+"Religion: "+Religion+"\n"+"Time: "+Time+"\n"+line+"\n";

set_user_layout(Email,User_id, Name, Age, Gender, Level_of_Education, Marital_Status, No_of_Children, Pregnancy_Status, Religion, Time);

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void load_details(final int x, final String query2) {

        String score_url = UserDetails.database_url +query2+"/Course_"+x;
        Firebase Score_reference = new Firebase(score_url);

        Query queryRef = Score_reference.orderByChild("Email");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);

                if (query2.equals("Course_Stats")){
                    String email = map.get("email").toString();
                    String Username = map.get("username").toString().toLowerCase();
                    String Time = map.get("Time").toString();
                    String attempts = map.get("attempts").toString();
                    String average_score = map.get("average_score").toString();
                    String failed = map.get("failed").toString();
                    String success = map.get("success").toString();
                    String total_score = map.get("total_score").toString();

                    set_stats_layout(email, Username, Time, attempts, average_score, failed, success, total_score, x);

                    singleList = new ArrayList<String>();

                    singleList.clear();

                    singleList.add(email);
                    singleList.add(Username);
                    singleList.add(attempts);
                    singleList.add(success);
                    singleList.add(failed);
                    singleList.add(total_score);
                    singleList.add(average_score);
                    singleList.add(Time);

                    listOfLists.add(singleList);

                }
                else if (query2.equals("Course_Scores")){
                    String email = map.get("Email").toString();
                    String Username = map.get("Username").toString().toLowerCase();
                    String Pre_ass_score = map.get("Pre_ass_score").toString();
                    String score = map.get("Score").toString();
                    String Status = map.get("Status").toString();
                    String Time = map.get("Time").toString().toLowerCase();

                    set_scores_layout(email, Pre_ass_score, score, Status, Time, Username, x);

                    singleList = new ArrayList<String>();

                    singleList.clear();

                    singleList.add(email);
                    singleList.add(Username);
                    singleList.add(Pre_ass_score);
                    singleList.add(score);
                    singleList.add(Status);
                    singleList.add(Time);

                    listOfLists.add(singleList);

                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void set_stats_layout(String email, String username, String time, String attempts, String average_score, String failed, String success, String total_score, int x) {

        switch (x){
            case 1:
                course1_layout = (LinearLayout)findViewById(R.id.course1_layout);
                break;
            case 2:
                course1_layout = (LinearLayout)findViewById(R.id.course2_layout);
                break;
            case 3:
                course1_layout = (LinearLayout)findViewById(R.id.course3_layout);
                break;
            case 4:
                course1_layout = (LinearLayout)findViewById(R.id.course4_layout);
                break;
            case 5:
                course1_layout = (LinearLayout)findViewById(R.id.course5_layout);
                break;
            case 6:
                course1_layout = (LinearLayout)findViewById(R.id.course6_layout);
                break;
        }

        TextView t1 = new TextView(this);
        t1.setText("Email: "+email);
        TextView t2 = new TextView(this);
        t2.setText("Username: "+username);
        TextView t3 = new TextView(this);
        t3.setText("Attempts: "+attempts);
        TextView t4 = new TextView(this);
        t4.setText("Average_score; "+average_score);
        TextView t5 = new TextView(this);
        t5.setText("Failed: "+failed);
        TextView t6 = new TextView(this);
        t6.setText("Success: "+success);
        TextView t7 = new TextView(this);
        t7.setText("Total_score: "+total_score);
        TextView t8 = new TextView(this);
        t8.setText("Time: "+time);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundResource(R.drawable.rounded_edittext6);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ll.addView(t1);
        ll.addView(t2);
        ll.addView(t3);
        ll.addView(t4);
        ll.addView(t5);
        ll.addView(t6);
        ll.addView(t7);
        ll.addView(t8);

        course1_layout.addView(ll);

        TextView space = new TextView(this);
        space.setText(" ");
        space.setTextSize(14);

        course1_layout.addView(space);

    }

    private void set_scores_layout(String email, String pre_ass_score, String score, String status, String time, String username, int x) {

        switch (x){
            case 1:
                course1_layout = (LinearLayout)findViewById(R.id.course1_layout);
                break;
            case 2:
            course1_layout = (LinearLayout)findViewById(R.id.course2_layout);
            break;
            case 3:
            course1_layout = (LinearLayout)findViewById(R.id.course3_layout);
            break;
            case 4:
            course1_layout = (LinearLayout)findViewById(R.id.course4_layout);
            break;
            case 5:
            course1_layout = (LinearLayout)findViewById(R.id.course5_layout);
            break;
            case 6:
            course1_layout = (LinearLayout)findViewById(R.id.course6_layout);
            break;
        }
        TextView t1 = new TextView(this);
        t1.setText("Email: "+email);
        TextView t2 = new TextView(this);
        t2.setText("Username: "+username);
        TextView t3 = new TextView(this);
        t3.setText("Pre_ass_score: "+pre_ass_score);
        TextView t4 = new TextView(this);
        t4.setText("Score; "+score);
        TextView t5 = new TextView(this);
        t5.setText("Status: "+status);
        TextView t6 = new TextView(this);
        t6.setText("Time: "+time);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundResource(R.drawable.rounded_edittext6);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ll.addView(t1);
        ll.addView(t2);
        ll.addView(t3);
        ll.addView(t4);
        ll.addView(t5);
        ll.addView(t6);

        course1_layout.addView(ll);

        TextView space = new TextView(this);
        space.setText(" ");
        space.setTextSize(14);

        course1_layout.addView(space);
    }

    private void set_user_layout(String email, String user_id, String name, String age, String gender, String level_of_Education, String marital_Status, String no_of_Children, String pregnancy_Status, String religion, String time) {


        course1_layout = (LinearLayout)findViewById(R.id.course1_layout);

        TextView t1 = new TextView(this);
        t1.setText("Email: "+email);
        TextView t2 = new TextView(this);
        t2.setText("Username: "+ name);
        TextView t3 = new TextView(this);
        t3.setText("User id: "+ user_id);
        TextView t4 = new TextView(this);
        t4.setText("Age; "+age);
        TextView t5 = new TextView(this);
        t5.setText("Gender: "+gender);
        TextView t6 = new TextView(this);
        t6.setText("Level of Education: "+level_of_Education);
        TextView t7 = new TextView(this);
        t7.setText("Marital Status: "+marital_Status);
        TextView t8 = new TextView(this);
        t8.setText("No of Children: "+no_of_Children);
        TextView t9 = new TextView(this);
        t9.setText("Pregnancy Status: "+pregnancy_Status);
        TextView t10 = new TextView(this);
        t10.setText("Religion: "+religion);
        TextView t11 = new TextView(this);
        t11.setText("Time: "+time);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundResource(R.drawable.rounded_edittext6);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ll.addView(t1);
        ll.addView(t2);
        ll.addView(t3);
        ll.addView(t4);
        ll.addView(t5);
        ll.addView(t6);
        ll.addView(t7);
        ll.addView(t8);
        ll.addView(t9);
        ll.addView(t10);
        ll.addView(t11);

        course1_layout.addView(ll);

        TextView space = new TextView(this);
        space.setText(" ");
        space.setTextSize(14);

        course1_layout.addView(space);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        closeOption = menu.findItem(R.id.action_settings);
        closeOption.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
            String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));

            String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
            String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

            String Time = hour+":"+minute+" "+day+"/"+month+"/"+year;

            File folder = new File(Environment.getExternalStorageDirectory() +  File.separator + "Breast_feeding_101");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }

            File output = new File(Environment.getExternalStorageDirectory() + File.separator + "Breast_feeding_101/", ".nomedia");
            boolean fileCreated = false;
            try {
                fileCreated = output.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (query.equals("Users")){
                create_users_pdf(Time);

            }
            else if (query.equals("Course_Scores")){
                create_scores_pdf(Time);
            }

            else if (query.equals("Course_Stats")){
                create_stats_pdf(Time);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void create_scores_pdf(String Time) {

        		File outputFile = new File(Environment.getExternalStorageDirectory() + File.separator + "i_EAT", "kolascore.pdf");
//        String FILE = "Kuti Hall Result.pdf";
        Document document21 = new Document(PageSize.A4, 0,0,0,0);
        try {
            PdfWriter writer = PdfWriter.getInstance(document21, new FileOutputStream(outputFile));

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document21.open();

        Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.BOLD);

        Paragraph subPara8 = new Paragraph("BF_101 Score Database", subFont);
        subPara8.setSpacingBefore(30);
        subPara8.setAlignment(Element.ALIGN_CENTER);
        try {
            document21.add(subPara8);
        } catch (DocumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        PdfPTable table = new PdfPTable(7);
        table.setSpacingBefore(20);
        table.setHeaderRows(1);

        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        float[] columnWidths = new float[] {12f, 60f, 20f, 18f, 12f, 13f, 31f};


        table.setWidthPercentage(90);


        try {
            table.setWidths(columnWidths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        PdfPCell c1 = new PdfPCell(new Phrase("S/N"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Email"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("P.A Score"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Score"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Status"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Time"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        for(int x = 0; x < listOfLists.size(); x++)
        {
            String email = listOfLists.get(x).get(0);
            String name = listOfLists.get(x).get(1);
            String pre_ass_score = listOfLists.get(x).get(2);
            String score = listOfLists.get(x).get(3);
            String status = listOfLists.get(x).get(4);
            String time = listOfLists.get(x).get(5);

            c1 = new PdfPCell(new Phrase((x+1)+""));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(email));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(name));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(pre_ass_score));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(score));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(status));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(time));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }

        Paragraph subPara = new Paragraph("");
        subPara.add(table);
        try {
            document21.add(subPara);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document21.close();

    }

    private void create_stats_pdf(String Time) {

        		File outputFile = new File(Environment.getExternalStorageDirectory() + File.separator + "i_EAT", "kolastats.pdf");
//        String FILE = "Kuti Hall Result.pdf";
        Document document21 = new Document(PageSize.A4, 0,0,0,0);
        try {
            PdfWriter writer = PdfWriter.getInstance(document21, new FileOutputStream(outputFile));

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document21.open();

        Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.BOLD);

        Paragraph subPara8 = new Paragraph("BF_101 Score Database", subFont);
        subPara8.setSpacingBefore(30);
        subPara8.setAlignment(Element.ALIGN_CENTER);
        try {
            document21.add(subPara8);
        } catch (DocumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        PdfPTable table = new PdfPTable(9);
        table.setSpacingBefore(20);
        table.setHeaderRows(1);

        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        float[] columnWidths = new float[] {12f, 50f, 17f, 16f, 15f, 11f, 12f, 15f, 29f};


        table.setWidthPercentage(100);


        try {
            table.setWidths(columnWidths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        PdfPCell c1 = new PdfPCell(new Phrase("S/N"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Email"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Attempts"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Success"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Failed"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total Score"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Average Score"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Time"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);



        for(int x = 0; x < listOfLists.size(); x++)
        {
            String email = listOfLists.get(x).get(0);
            String name = listOfLists.get(x).get(1);
            String attempts = listOfLists.get(x).get(2);
            String success = listOfLists.get(x).get(3);
            String failed = listOfLists.get(x).get(4);
            String total_score = listOfLists.get(x).get(5);
            String average_score = listOfLists.get(x).get(6);
            String time = listOfLists.get(x).get(7);

            c1 = new PdfPCell(new Phrase((x+1)+""));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(email));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(name));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(attempts));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(success));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(failed));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(total_score));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(average_score));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(time));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }

        Paragraph subPara = new Paragraph("");
        subPara.add(table);
        try {
            document21.add(subPara);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document21.close();
    }

    private void create_users_pdf(String time) {


        String file_name = "BF_101_Users_"+time+".pdf";

        File outputFile = new File(Environment.getExternalStorageDirectory() + File.separator + "i_EAT", "kolausers.pdf");

        Document document21 = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document21, new FileOutputStream(outputFile));

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document21.open();

        Paragraph subPara2 = new Paragraph(users);
        subPara2.setAlignment(Element.ALIGN_LEFT);

        try {
            document21.add(subPara2);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document21.close();

    }
}