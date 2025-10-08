package com.example.decathlon.core;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ScoringService {
    public enum Type { TRACK, FIELD }
    public enum Mode { DEC, HEP }
    public record EventDef(String id, Type type, double A, double B, double C, String unit) {}

    private final Map<String, EventDef> decEvents = Map.of(
            "100m",       new EventDef("100m",       Type.TRACK, 25.4347, 18.0, 1.81, "s"),
            "longJump",   new EventDef("longJump",   Type.FIELD, 0.14354, 220.0, 1.40, "cm"),
            "shotPut",    new EventDef("shotPut",    Type.FIELD, 51.39,   1.5,  1.05, "m"),
            "highJump",   new EventDef("highJump",   Type.FIELD, 0.8465,  75.0, 1.42, "cm"),
            "400m",       new EventDef("400m",       Type.TRACK, 1.53775, 82.0, 1.81, "s"),
            "110mHurdles",new EventDef("110mHurdles",Type.TRACK, 5.74352, 28.5, 1.92, "s"),
            "discus",     new EventDef("discus",     Type.FIELD, 12.91,   4.0,  1.10, "m"),
            "poleVault",  new EventDef("poleVault",  Type.FIELD, 0.2797, 100.0, 1.35, "cm"),
            "javelin",    new EventDef("javelin",    Type.FIELD, 10.14,   7.0,  1.08, "m"),
            "1500m",      new EventDef("1500m",      Type.TRACK, 0.03768, 480.0,1.85, "s")
    );

    private final Map<String, EventDef> hepEvents = Map.of(
            "100mHurdles",new EventDef("100mHurdles",Type.TRACK, 9.23076, 26.7, 1.835,"s"),
            "highJump",   new EventDef("highJump",   Type.FIELD, 1.84523, 75.0, 1.348,"cm"),
            "shotPut",    new EventDef("shotPut",    Type.FIELD, 56.0211, 1.5,  1.05, "m"),
            "200m",       new EventDef("200m",       Type.TRACK, 4.99087, 42.5, 1.81, "s"),
            "longJump",   new EventDef("longJump",   Type.FIELD, 0.188807,210.0,1.41, "cm"),
            "javelin",    new EventDef("javelin",    Type.FIELD, 15.9803, 3.8,  1.04, "m"),
            "800m",       new EventDef("800m",       Type.TRACK, 0.11193, 254.0,1.88, "s")
    );

    public EventDef get(Mode mode, String id) {
        return mode == Mode.DEC ? decEvents.get(id) : hepEvents.get(id);
    }

    public int score(Mode mode, String eventId, double raw) {
        EventDef e = get(mode, eventId);
        if (e == null) return 0;
        double points;
        if (e.type == Type.TRACK) {
            double x = e.B - raw;
            if (x <= 0) return 0;
            points = e.A * Math.pow(x, e.C);
        } else {
            double x = raw - e.B;
            if (x <= 0) return 0;
            points = e.A * Math.pow(x, e.C);
        }
        return (int)Math.floor(points);
    }
}
