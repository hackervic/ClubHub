package com.nucleus.events.clubhub;

public class Model_grid_item {

    String grid_image  , grid_club_title , grid_club_catagor,grid_club_collage , grid_club_rating;

    public Model_grid_item() {
    }

    public Model_grid_item(String grid_image, String grid_club_title, String grid_club_catagor, String grid_club_collage, String grid_club_rating) {
        this.grid_image = grid_image;
        this.grid_club_title = grid_club_title;
        this.grid_club_catagor = grid_club_catagor;
        this.grid_club_collage = grid_club_collage;
        this.grid_club_rating = grid_club_rating;
    }

    public String getGrid_image() {
        return grid_image;
    }

    public void setGrid_image(String grid_image) {
        this.grid_image = grid_image;
    }

    public String getGrid_club_title() {
        return grid_club_title;
    }

    public void setGrid_club_title(String grid_club_title) {
        this.grid_club_title = grid_club_title;
    }

    public String getGrid_club_catagor() {
        return grid_club_catagor;
    }

    public void setGrid_club_catagor(String grid_club_catagor) {
        this.grid_club_catagor = grid_club_catagor;
    }

    public String getGrid_club_collage() {
        return grid_club_collage;
    }

    public void setGrid_club_collage(String grid_club_collage) {
        this.grid_club_collage = grid_club_collage;
    }

    public String getGrid_club_rating() {
        return grid_club_rating;
    }

    public void setGrid_club_rating(String grid_club_rating) {
        this.grid_club_rating = grid_club_rating;
    }
}
