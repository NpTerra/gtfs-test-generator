package dev.rnandor.gtfstestgenerator.io;

import com.univocity.parsers.csv.CsvRoutines;
import com.univocity.parsers.csv.CsvWriterSettings;
import dev.rnandor.gtfstestgenerator.model.inner.Agency;
import dev.rnandor.gtfstestgenerator.model.inner.Calendar;
import dev.rnandor.gtfstestgenerator.model.inner.GTFSModel;
import dev.rnandor.gtfstestgenerator.model.inner.Route;
import dev.rnandor.gtfstestgenerator.model.inner.Station;
import dev.rnandor.gtfstestgenerator.model.inner.StopTime;
import dev.rnandor.gtfstestgenerator.model.inner.Trip;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class GTFSModelWriter {
    private GTFSModelWriter() {}

    public static void writeModelToFile(GTFSModel model, Path dir, String name) {
        try(var fos = new FileOutputStream(dir.resolve(name).toFile());
            var zos = new ZipOutputStream(fos)
        ) {
            writeCsvToFile(List.of(model.getAgency()), zos, "agency.txt", Agency.class);
            writeCsvToFile(model.getStations(), zos, "stops.txt", Station.class);
            writeCsvToFile(model.getRoutes(), zos, "routes.txt", Route.class);
            writeCsvToFile(model.getTrips(), zos, "trips.txt", Trip.class);
            writeCsvToFile(model.getCalendars(), zos, "calendar.txt", Calendar.class);
            writeCsvToFile(model.getStopTimes(), zos, "stop_times.txt", StopTime.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> void writeCsvToFile(List<T> data, ZipOutputStream zos, String name, Class<T> clazz) throws IOException {
        byte[] csv = toCsvBytes(data, clazz);
        zos.putNextEntry(new ZipEntry(name));
        zos.write(csv);
        zos.closeEntry();
    }

    public static <T> byte[] toCsvBytes(List<T> data, Class<T> clazz) throws IOException {
        var baos = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8);

        CsvWriterSettings settings = new CsvWriterSettings();
        settings.setHeaderWritingEnabled(true);

        var routines = new CsvRoutines(settings);
        routines.writeAll(data, clazz, writer);

        //writer.flush();
        return baos.toByteArray();
    }
}
