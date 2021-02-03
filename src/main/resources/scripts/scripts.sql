CREATE DATABASE site_maintenance;

CREATE TABLE sites(site_id serial PRIMARY KEY, site_name VARCHAR, site_location VARCHAR);

CREATE TABLE engineers(eng_id serial PRIMARY KEY, eng_names VARCHAR);

CREATE TABLE engineers_sites(site_id VARCHAR, eng_id VARCHAR);

CREATE TABLE site_notes(note_id serial PRIMARY KEY, note VARCHAR, site_id VARCHAR);


