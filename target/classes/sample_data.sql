-- Insert Users
INSERT INTO user_data (name, email, password, is_verified, country, role, created_at, updated_at)
VALUES 
('Alice Johnson', 'alice@example.com', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJhbGljZSIsImFkbWluIjp0cnVlLCJpYXQiOjE1MTYyMzkwMjJ9.67sBfmSQ5PQ2p86IO42eyQAFinFVdDd7X2PTUVxnDoQ', TRUE, 'USA', 'USER', now(), now()),
('Bob Smith', 'bob@example.com', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJib2IiLCJhZG1pbiI6dHJ1ZSwiaWF0IjoxNTE2MjM5MDIyfQ.UAJwRkcCapNideNqdEPeTbO9v-FSm-kKnuhjHA1bUIw', TRUE, 'USA', 'USER', now(), now()),
('Charlie Admin', 'admin@example.com', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJDaGFybGllIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.lew3KnGrofwys7Igt-KLTK8LLRXqnrIyz9gvdnClAEI', TRUE, 'USA', 'ADMIN', now(), now());

-- Insert Packages
INSERT INTO package_data (name, country, credits, price, valid_days, is_available, created_at, updated_at)
VALUES
('Starter Pack', 'USA', 10, 99.99, 30, TRUE, now(), now()),
('Pro Pack', 'USA', 25, 199.99, 60, TRUE, now(), now());

-- Insert Class Schedules
INSERT INTO class_schedule (title, country, required_credits, max_slots, start_time, end_time, is_ended)
VALUES
('Yoga Basics', 'USA', 2, 20, '2025-07-01 10:00:00', '2025-07-01 11:00:00', FALSE),
('Advanced Pilates', 'USA', 3, 15, '2025-07-02 14:00:00', '2025-07-02 15:00:00', FALSE);

-- Insert User Packages
INSERT INTO user_package (user_data_id, package_data_id, remaining_credits, purchase_date, expiry_date)
VALUES
(1, 1, 8, now(), now() + interval '30 days'),
(2, 2, 25, now(), now() + interval '60 days');

-- Insert Bookings
INSERT INTO booking (user_data_id, class_schedule_id, user_package_id, booking_time, status)
VALUES
(1, 1, 1, now(), 'BOOKED'),
(2, 2, 2, now(), 'BOOKED');

-- Insert Waitlists
INSERT INTO waitlist (user_data_id, class_schedule_id, added_at, moved_to_booking)
VALUES
(2, 1, now(), FALSE);
