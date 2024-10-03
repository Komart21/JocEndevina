package com.project.endevinampes;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerRecord implements Parcelable {
    private final String name;
    private final int attempts;
    private final int gamesPlayed;

    // Constructor
    public PlayerRecord(String name, int attempts, int gamesPlayed) {
        this.name = name;
        this.attempts = attempts;
        this.gamesPlayed = gamesPlayed;
    }

    // Constructor para Parcel
    protected PlayerRecord(Parcel in) {
        name = in.readString();
        attempts = in.readInt();
        gamesPlayed = in.readInt();
    }

    // Método para crear el objeto desde el Parcel
    public static final Creator<PlayerRecord> CREATOR = new Creator<PlayerRecord>() {
        @Override
        public PlayerRecord createFromParcel(Parcel in) {
            return new PlayerRecord(in);
        }

        @Override
        public PlayerRecord[] newArray(int size) {
            return new PlayerRecord[size];
        }
    };

    // Método para escribir los datos en el Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(attempts);
        dest.writeInt(gamesPlayed);
    }

    // Método para describir los contenidos del Parcel
    @Override
    public int describeContents() {
        return 0;
    }

    // Métodos getter
    public String getName() {
        return name;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
}
