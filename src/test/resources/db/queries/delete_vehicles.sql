DELETE FROM public.vehicle;

ALTER TABLE public.vehicle ALTER COLUMN id RESTART WITH 1;

