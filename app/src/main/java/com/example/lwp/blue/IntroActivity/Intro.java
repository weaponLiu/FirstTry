package com.example.lwp.blue.IntroActivity;

/**
 * Created by WP on 2018/4/11.
 */
public class Intro {
        private String name;
        private String intent1,intent2;
        public  Intro(String name, String intent1, String intent2){
            this.name = name;
            this.intent1 = intent1;
            this.intent2 = intent2;
        }
        public String getName() {
            return name;
        }
        public String getIntentOne(){
            return intent1;
        }
        public String getIntentTwo(){
            return intent2;
    }
}
