package com.actv8.k2annex.actv8mediaplayer.Model;

import java.util.ArrayList;

/**
 * Created by mgriego on 5/1/17.
 */

public class ContentObject
{
    int id;
    String external_id;
    String source;
    String legacy_id;
    String migrated_at;
    String actv8_reference_id;
    String name;
    String brand;
    String display_name;
    String type;
    String description;
    String uuid;
    String start_at;
    String end_at;
    String value;
    String currency;
    String url;
    String discount_percent;
    //String prizeRules_url;
    int quantity;
    int saved_amount;
    boolean active;
    boolean limit;
    Scratcher scratcher;
    Redemption redemption;
    Images images;
    int content_term_id;
    boolean apple_pass_enabled;
    boolean android_pass_enabled;
    String created_at;
    String updated_at;
    String deleted_at;
    String detected_at;
    String historyLabel;
    int status;
    Trigger trigger;
    boolean isExpired;
    Pivot pivot;

    ArrayList<ButtonValue> buttons;

    public ContentObject()
    {

    }

    /*public ContentObject(ContentObject contentObject)
    {
        this.status = contentObject.getStatus();
        this.id = contentObject.getId();
        this.updated_at = contentObject.getUpdated_at();
        this.external_id = contentObject.getExternal_id();
        this.source = contentObject.getSource();
        this.legacy_id = contentObject.getLegacy_id();
        this.migrated_at = contentObject.getMigrated_at();
        this.actv8_reference_id = contentObject.getActv8_reference_id();
        this.name = contentObject.getName();
        this.brand = contentObject.getBrand();
        this.display_name = contentObject.getDisplay_name();
        this.type = contentObject.getType();
        this.description = contentObject.getDescription();
        this.uuid = contentObject.getUuid();
        this.start_at = contentObject.getStart_at();
        this.end_at = contentObject.getEnd_at();
        this.value = contentObject.getValue();
        this.currency = contentObject.getCurrency();
        this.discount_percent = contentObject.getDiscount_percent();
        this.quantity = contentObject.getQuantity();
        this.saved_amount = contentObject.getSaved_amount();
        this.active = contentObject.isActive();
        this.limit = contentObject.isLimit();
        this.scratcher = contentObject.getScratcher();
        this.redemption = contentObject.getRedemption();
        this.images = contentObject.getImages();
        this.content_term_id = contentObject.getContent_term_id();
        this.created_at = contentObject.getCreated_at();
        this.deleted_at = contentObject.getDeleted_at();
    }*/
    public class Scratcher
    {
        ContentImage images;
        boolean enabled;
        String description;
        String exclamation;


        public ContentImage getImages() {
            return images;
        }

        public void setImages(ContentImage images) {
            this.images = images;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getExclamation() {
            return exclamation;
        }

        public void setExclamation(String exclamation) {
            this.exclamation = exclamation;
        }
    }

    public class ContentImage
    {
        Image hero;
        Image overlay;

        public Image getHero()
        {
            return hero;
        }

        public void setHero(Image hero)
        {
            this.hero = hero;
        }

        public Image getOverlay()
        {
            return overlay;
        }

        public void setOverlay(Image overlay)
        {
            this.overlay = overlay;
        }
    }

    public class Image
    {
        String url;
        String filename;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }

    public class Redemption
    {
        String url;
        int method;
        int time_limit;
        Code code;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getMethod() {
            return method;
        }

        public void setMethod(int method) {
            this.method = method;
        }

        public int getTime_limit() {
            return time_limit;
        }

        public void setTime_limit(int time_limit) {
            this.time_limit = time_limit;
        }

        public Code getCode() {
            return code;
        }

        public void setCode(Code code) {
            this.code = code;
        }

        public class Code
        {
            String value;
            String format;
            String source;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }
        }
    }

    public class Images
    {
        public Image hero;
        public Image thumbnail;
        public Image in_body;

        public Image getHero()
        {
            return hero;
        }

        public void setHero(Image hero)
        {
            this.hero = hero;
        }

        public Image getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(Image thumbnail) {
            this.thumbnail = thumbnail;
        }

        public Image getIn_body() {
            return in_body;
        }

        public void setIn_body(Image in_body) {
            this.in_body = in_body;
        }
    }

    public class Trigger
    {
        int id;
        String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public class Pivot
    {
        int user_id;
        int content_id;
        String created_at;
        String updated_at;
        int status;
        int trigger_id;
        int campaign_id;
        String redeemed_at;
        int scratcher_set_id;
        String saved_at;
        String declined_at;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getContent_id() {
            return content_id;
        }

        public void setContent_id(int content_id) {
            this.content_id = content_id;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTrigger_id() {
            return trigger_id;
        }

        public void setTrigger_id(int trigger_id) {
            this.trigger_id = trigger_id;
        }

        public int getCampaign_id() {
            return campaign_id;
        }

        public void setCampaign_id(int campaign_id) {
            this.campaign_id = campaign_id;
        }

        public String getRedeemed_at() {
            return redeemed_at;
        }

        public void setRedeemed_at(String redeemed_at) {
            this.redeemed_at = redeemed_at;
        }

        public int getScratcher_set_id() {
            return scratcher_set_id;
        }

        public void setScratcher_set_id(int scratcher_set_id) {
            this.scratcher_set_id = scratcher_set_id;
        }

        public String getSaved_at() {
            return saved_at;
        }

        public void setSaved_at(String saved_at) {
            this.saved_at = saved_at;
        }

        public String getDeclined_at() {
            return declined_at;
        }

        public void setDeclined_at(String declined_at) {
            this.declined_at = declined_at;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLegacy_id() {
        return legacy_id;
    }

    public void setLegacy_id(String legacy_id) {
        this.legacy_id = legacy_id;
    }

    public String getMigrated_at() {
        return migrated_at;
    }

    public void setMigrated_at(String migrated_at) {
        this.migrated_at = migrated_at;
    }

    public String getActv8_reference_id() {
        return actv8_reference_id;
    }

    public void setActv8_reference_id(String actv8_reference_id) {
        this.actv8_reference_id = actv8_reference_id;
    }


   /* public String getPrizeRules_url() {
        return prizeRules_url;
    }

    public void setPrizeRules_url(String prizeRules_url) {
        this.prizeRules_url = prizeRules_url;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(String discount_percent) {
        this.discount_percent = discount_percent;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSaved_amount() {
        return saved_amount;
    }

    public void setSaved_amount(int saved_amount) {
        this.saved_amount = saved_amount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isLimit() {
        return limit;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }

    public Scratcher getScratcher() {
        return scratcher;
    }

    public void setScratcher(Scratcher scratcher) {
        this.scratcher = scratcher;
    }

    public Redemption getRedemption() {
        return redemption;
    }

    public void setRedemption(Redemption redemption) {
        this.redemption = redemption;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public int getContent_term_id() {
        return content_term_id;
    }

    public void setContent_term_id(int content_term_id) {
        this.content_term_id = content_term_id;
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

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isApple_pass_enabled() {
        return apple_pass_enabled;
    }

    public void setApple_pass_enabled(boolean apple_pass_enabled) {
        this.apple_pass_enabled = apple_pass_enabled;
    }

    public boolean isAndroid_pass_enabled() {
        return android_pass_enabled;
    }

    public void setAndroid_pass_enabled(boolean android_pass_enabled) {
        this.android_pass_enabled = android_pass_enabled;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

    public String getDetected_at() {
        return detected_at;
    }

    public void setDetected_at(String detected_at) {
        this.detected_at = detected_at;
    }


    public String getHistoryLabel() {
        return historyLabel;
    }

    public void setHistoryLabel(String historyLabel) {
        this.historyLabel = historyLabel;
    }

    public ArrayList<ButtonValue> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<ButtonValue> buttons) {
        this.buttons = buttons;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
