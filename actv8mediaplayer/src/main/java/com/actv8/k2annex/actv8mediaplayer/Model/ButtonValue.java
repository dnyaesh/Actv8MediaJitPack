package com.actv8.k2annex.actv8mediaplayer.Model;

/**
 * Created by neoforce-01 on 2/15/2019.
 */

public class ButtonValue
{
    int content_id;
    String key;
    String label;
    String locale;
    String client_id;
    Pivot pivot;

    public class Pivot
    {
        int content_id;
        int button_key_id;
        String created_at;
        String updated_at;
        int button_label_id;

        public int getContent_id() {
            return content_id;
        }

        public void setContent_id(int content_id) {
            this.content_id = content_id;
        }

        public int getButton_key_id() {
            return button_key_id;
        }

        public void setButton_key_id(int button_key_id) {
            this.button_key_id = button_key_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getButton_label_id() {
            return button_label_id;
        }

        public void setButton_label_id(int button_label_id) {
            this.button_label_id = button_label_id;
        }
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }
}
