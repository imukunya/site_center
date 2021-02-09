CREATE DATABASE site_maintenance;

CREATE TABLE sites(site_id serial PRIMARY KEY, site_name VARCHAR, site_location VARCHAR);

CREATE TABLE engineers(eng_id serial PRIMARY KEY, eng_names VARCHAR);

CREATE TABLE engineers_sites(site_id VARCHAR, eng_id VARCHAR);

CREATE TABLE site_notes(note_id serial PRIMARY KEY, note VARCHAR, site_id VARCHAR);


SELECT sites.*,engineers.* FROM engineers
JOIN engineers_sites ON (engineers.eng_id::varchar = engineers_sites.eng_id)
JOIN sites ON (sites.site_id::varchar = engineers_sites.site_id);