package irrigation.abox;


/*
 * @author Randy Massafra, Scott Streit
 * @version 0.1
 */
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Logger;

import javax.sound.midi.Patch;

import irrigation.generated.Irrigation;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * LoadAwardsData
 * 
 * Based on NITRD provided NSF data in awards.xml
 */
public class LoadOrchard {

	final static Logger logger = Logger.getLogger(LoadOrchard.class.getName());

	/**
	 * Pull File(s) from provided path and pass on for processing
	 * 
	 * @param arg
	 *            [0] = input file/directory Path
	 * @param arg
	 *            [1] = output directory Path
	 */
	public static void main(String args[]) throws Exception {
		File file = new File(args[0]);
		String fileName = args[1];
		logger.info(" file is " + file.getName());
		logger.info(" fileName is " + fileName);

		String destDir = file.getAbsolutePath().endsWith(File.separator) ? file
				.getAbsolutePath() : file.getAbsolutePath() + File.separator;
		logger.info(" destDir = " + destDir);


		try {
			doFile(destDir, fileName);
		} catch (Exception e) {
			logger.severe("error processing file " + file.getName()
					+ "absolute name " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}

	/**
	 * 	  
	 * Strips data from the xml file and passes on for processing into a jena
	 * model.
	 * 
	 * @param fileName
	 *            - the input file.
	 * @param destDir
	 *            - the destination directory to place the Abox results.
	 */
	private static void doFile(String destDir, String fileName)
			throws FileNotFoundException {

		Model model = ModelFactory.createDefaultModel();
		insertWithProvenance(model);

//		 Output model
		logger.fine("completed processing, outputting data");
		File outFile = new File(destDir + "orchard" + ".nt");
		PrintStream ps = new PrintStream(outFile);
		logger.info("completed processing, writing out N-TRIPLE");
		model.write(ps, "N-TRIPLE");
		logger.info("process completed successfully");
	}

	/**
	 * Inserts provided data into the Jena model
	 * 
	 * @param model
	 *            - the input model.
	 * 
	 */
	public static void insertWithProvenance(Model model) {

		// create a farmer
		Individual farmer = createIndividual(model, "Farmer John",
				"610-286-0123", "farmerjohn@morgantownorchard.com",
				"Morgantown Orchard");

		// create a helper - should fail the rule is a Farmer
		Individual helper = createIndividual(model, "Helper Jane",
				"610-286-0123", "helperjane@morgantownorchard.com", 
				"Morgantown Orchard");
		
		// create a farm
		String[] patchTypes = {"blueberry patch", "strawberry patch", "tomato patch"};
		Individual farm = createFarm(model,"Morgantown Orchard", "Farmer John", "clay soil", patchTypes);
		
		// create a strawberry patch
		Individual strawberryPatch = createPatch(model, "Strawberry Patch", "104534", "106543");
		
		// create a blueberry patch
		Individual blueberryPatch = createPatch(model, "Blueberry Patch", "105344", "103423");
		
		// create a strawberry plant
		Individual strawberryPlant = createPlant(model, "Strawberry plant", "1.25", "4.23288", ".00005");
		
		// create a blueberry plant
		Individual blueberryPlant = createPlant(model, "Blueberry plant", "2.35", ".3525", ".00005");
		
		// create soil for strawberry plant
		Individual strawberrySoil = createSoil(model, "Strawberry plant", "1.26", "3.00", "1.85", "1.26");
		
		// create soil for blueberry plant
		Individual blueberrySoil = createSoil(model, "Blueberry plant", "1.96", "3.00", "2.25", "1.96");
		
		// create a sensor for strawberry plant
		Individual strawberrySensor = createSensor(model, "Acclima Digital TDT RS500", "dielectric constant", "80.4", "Strawberry plant");
		
		// create a sensor for blueberry plant
		Individual blueberrySensor = createSensor(model, "Acclima Digital TDT RS500", "dielectric constant", "80.4", "Blueberry plant");
		
	}


	private static void printStatements(StmtIterator si) {
		while (si.hasNext()) {
			Statement s = si.next();
			logger.info("Statement is " + s);
		}
	}

	/**
	 * Create a basic individual using vcard
	 * 
	 * @param model
	 * @param name
	 * @param phone
	 * @param email
	 * @param role
	 * @return VCard Individual
	 */
	public static Individual createIndividual(Model model, String name,
			String phone, String email, String orgName) {
		if (name == null && phone == null && email == null)
			return null;
		
		Individual farmer = Irrigation.VCard.createIndividual(Irrigation.VCard.getURI()
				+ System.currentTimeMillis());
		Statement s = null;
		
		s = model.createStatement(farmer, Irrigation.fn, name);
		model.add(s);
		s = model.createStatement(farmer, Irrigation.tel, phone);
		model.add(s);
		s = model.createStatement(farmer, Irrigation.email, email);
		model.add(s);
		s = model.createStatement(farmer, Irrigation.organization_name, orgName);
		model.add(s);
		return farmer;
	}


	public static Individual createFarm(Model model, String name, String owner,
			String soilType, String[] patchTypes) {
		
		Individual farm = Irrigation.Farmland.createIndividual(Irrigation.Farmland.getURI()
				+ System.currentTimeMillis());
		Statement s = null;
		s = model.createStatement(farm, Irrigation.name, name);
		model.add(s);
		s = model.createStatement(farm, Irrigation.owner, owner);
		model.add(s);
		s = model.createStatement(farm, Irrigation.soil, soilType);
		model.add(s);
		for (String patchType : patchTypes) {
			s = model.createStatement(farm, Irrigation.patch, patchType);
			model.add(s);
		}
		
		return farm;
	}
	
	public static Individual createPatch(Model model, String name, String coordinateX, String coordinateY) {
		
		Individual patch = Irrigation.Patch.createIndividual(Irrigation.Patch.getURI() +
				System.currentTimeMillis());
		Statement s = null;
		s = model.createStatement(patch, Irrigation.name, name);
		model.add(s);
		s = model.createStatement(patch, Irrigation.coordinateX, coordinateX);
		model.add(s);
		s = model.createStatement(patch, Irrigation.coordinateY, coordinateY);
		model.add(s);
		
		return patch;
	}
	
	public static Individual createPlant(Model model, String plantName, String rootDepth,
			String maxDryWeight, String dailyGallonUsage) {
		
		Individual plant = Irrigation.Crop.createIndividual(Irrigation.Crop.getURI() +
				System.currentTimeMillis());
		Statement s = null;
		s = model.createStatement(plant, Irrigation.plantName, plantName);
		model.add(s);
		s = model.createStatement(plant, Irrigation.rootDepth, rootDepth);
		model.add(s);
		s = model.createStatement(plant, Irrigation.maxDryWeight, maxDryWeight);
		model.add(s);
		s = model.createStatement(plant, Irrigation.dailyGallonUsage, dailyGallonUsage);
		model.add(s);
		
		return plant;
	}
	
	public static Individual createSensor(Model model, String name, String measures, String currentValue, 
			String plantType) {
		
		Individual sensor = Irrigation.Sensor.createIndividual(Irrigation.Sensor.getURI() +
				System.currentTimeMillis());
		Statement s = null;
		s = model.createStatement(sensor, Irrigation.sensorName, name);
		model.add(s);
		s = model.createStatement(sensor, Irrigation.currentValue, currentValue);
		model.add(s);
		s = model.createStatement(sensor, Irrigation.measures, measures);
		model.add(s);
		s = model.createStatement(sensor, Irrigation.measuresPlantType, plantType);
		model.add(s);
		
		return sensor;
	}
	
	public static Individual createSoil(Model model, String plant, String saturationLevel, String maxSaturationLevel, String allowableDepletion,
			String availableWater) {
		
		Individual soil = Irrigation.Soil.createIndividual(Irrigation.Soil.getURI() +
				System.currentTimeMillis());
		Statement s = null;
		s = model.createStatement(soil, Irrigation.plantName, plant);
		model.add(s);
		s = model.createStatement(soil, Irrigation.saturationLevel, saturationLevel);
		model.add(s);
		s = model.createStatement(soil, Irrigation.allowableDepletion, allowableDepletion);
		model.add(s);
		s = model.createStatement(soil, Irrigation.availableWater, availableWater);
		model.add(s);
		
		return soil;
	}
	

}
