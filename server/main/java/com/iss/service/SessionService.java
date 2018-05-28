package com.iss.service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SessionService implements IService {
    public Session getSession(Long guid) {
        return sessions.get(guid);
    }

    public class Session {
        private Date expire;
        private String email;

        Session(Date expire, String email) {
            this.expire = expire;
            this.email = email;
        }

        public Date getExpire() {
            return expire;
        }

        public void setExpire(Date expire) {
            this.expire = expire;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    private final AtomicLong guid = new AtomicLong(Long.MIN_VALUE);
    private final ConcurrentHashMap<Long, Session> sessions = new ConcurrentHashMap<>();
    public SessionService() {

    }

    public Long createSession(String email) {
        long guidValue = guid.incrementAndGet();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 30);

        Session session = new Session(cal.getTime(), email);
        sessions.put(guidValue, session);

        return guidValue;
    }

    public boolean exists(long guid) {
        return sessions.containsKey(guid);
    }
}
