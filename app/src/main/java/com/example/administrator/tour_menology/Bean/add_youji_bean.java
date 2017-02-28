package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/9/24 15:42
 */
public class add_youji_bean {

        private String name;
        private String id ;

        public add_youji_bean(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public add_youji_bean(String name, String id) {

            this.name = name;
            this.id = id;
        }


}
