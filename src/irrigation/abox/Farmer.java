package irrigation.abox;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;

import irrigation.generated.Irrigation;

import java.util.*;

public class Farmer extends AbstractOntClass
{
    public static final String FARMLAND_KEY = "FARMLAND";
    public static final String NAME_KEY = "FARMER_NAME";
    public static final String PHONE_KEY = "FARMER_PHONE";
    public static final String EMAIL_KEY = "FARMER_EMAIL";
    public static final String ORG_KEY = "FARMER_ORG";
    
    private Farmland farmland = null;
    String name = "";
    String phone = "";
    String email = ""; 
    String orgName = "";
    
    public Farmer(Model model)
    {
        this.model = model;
        this.individual = super.createIndividual(Irrigation.Farmer);
    }
    
    public void setFarmland(Farmland farmland)
    {
        removeExistingStatement(FARMLAND_KEY);
        this.farmland = farmland;
        
        statements.put(FARMLAND_KEY,
                model.createStatement(individual, Irrigation.owns, farmland.getIndividual()));
    }
    
    public void setName(String name)
    {
        removeExistingStatement(NAME_KEY);
        this.name = name;
        
        statements.put(NAME_KEY,
                model.createLiteralStatement(individual, Irrigation.name, name));
    }
    
    public void setPhone(String phone)
    {
        removeExistingStatement(PHONE_KEY);
        statements.put(PHONE_KEY,
                model.createLiteralStatement(individual, Irrigation.tel, phone));
        
        this.phone = phone;
    }
    
    public void setEmail(String email)
    {
        addLiteralStatement(EMAIL_KEY, Irrigation.emailAddress, email);
        this.email = email;
    }
    
    public void setOrginizationName(String organizationName)
    {
        addLiteralStatement(ORG_KEY, Irrigation.organization_name, organizationName);
        this.orgName = organizationName;
    }
    
    @Override
    public ArrayList<Statement> getStatements()
    {
        ArrayList <Statement> statementsList = super.getStatements();
        
        statementsList.addAll(farmland.getStatements());
        return statementsList;
    }
}
