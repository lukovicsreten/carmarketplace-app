-- Definiše TEXT kao alias za CLOB u H2 (jer entiteti koriste columnDefinition="TEXT")
CREATE DOMAIN IF NOT EXISTS TEXT AS CLOB;
