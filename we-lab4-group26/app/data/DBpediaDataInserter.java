package data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import play.Logger;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import at.ac.tuwien.big.we14.lab4.dbpedia.api.*;
import at.ac.tuwien.big.we14.lab4.dbpedia.vocabulary.*;

import models.Category;
import models.Choice;
import models.Question;
import models.QuizDAO;


public class DBpediaDataInserter {
	
	public static void insertData(){
		List<Category> jsonCategories = getData();
		for(Category category : jsonCategories)
			QuizDAO.INSTANCE.persist(category);
	}
	
	private static List<Category> getData(){
		List <Category> categories = new ArrayList<Category>();
		categories.add(getCategory());
		return categories;
	}
	
	private static Category getCategory(){
		Category category = new Category();
		category.setNameDE("Österreich");
		category.setNameEN("Austria");
		for(int i = 4; i >= 0; i--){
			category.addQuestion(getQuestion(i));
		}
		return category;
	}
	
	private static Question getQuestion(int i){
		Question q = new Question();
		if(i == 4){
			q.setTextDE("Wer war nie österreichischer Präsident?");
			q.setTextEN("Who has not been an austrian president?");
			q.setMaxTime(new BigDecimal(30));
			// build SPARQL-query
			SelectQueryBuilder autPresidentQuery = DBPediaService.createQueryBuilder()
					.addWhereClause(DBPProp.createResource("title"), DBPedia.createProperty("President_of_Austria"))
					.addPredicateExistsClause(DBPProp.createResource("name"))
					.addFilterClause(RDFS.label, Locale.GERMAN)
					.addFilterClause(RDFS.label, Locale.ENGLISH)
					.setLimit(3); // at most five statements;
			// retrieve data from dbpedia
			Model autPresidents = DBPediaService.loadStatements(autPresidentQuery.toQueryString());
			// get english and german movie names, e.g., for right choices
			List<String> autPresidentNamesEN =
					DBPediaService.getResourceNames(autPresidents, Locale.ENGLISH);
			List<String> autPresidentNamesDE =
					DBPediaService.getResourceNames(autPresidents, Locale.GERMAN);
			for(int y = 0; y < autPresidentNamesEN.size(); y++){
				Choice choice = new Choice();
				choice.setTextEN(autPresidentNamesEN.get(y));
				choice.setTextDE(autPresidentNamesDE.get(y));
				q.addWrongChoice(choice);
			}
			SelectQueryBuilder notAutPresidentQuery = DBPediaService.createQueryBuilder()
					.addWhereClause(DBPProp.createResource("title"), DBPedia.createProperty("President_of_Germany"))
					.addPredicateExistsClause(DBPProp.createResource("name"))
					.addFilterClause(RDFS.label, Locale.GERMAN)
					.addFilterClause(RDFS.label, Locale.ENGLISH)
					.setLimit(1); // at most five statements;
			// retrieve data from dbpedia
			Model notAutPresidents = DBPediaService.loadStatements(notAutPresidentQuery.toQueryString());
			// get english and german movie names, e.g., for right choices
			List<String> notAutPresidentNamesEN =
					DBPediaService.getResourceNames(notAutPresidents, Locale.ENGLISH);
			List<String> notAutPresidentNamesDE =
					DBPediaService.getResourceNames(notAutPresidents, Locale.GERMAN);
			Choice rChoice = new Choice();
			rChoice.setTextDE(notAutPresidentNamesDE.get(0));
			rChoice.setTextEN(notAutPresidentNamesEN.get(0));
			q.addRightChoice(rChoice);
		} else if(i == 3){
			q.setTextDE("Welche dieser Kanzler regierte unter Dr. Heinz Fischer?");
			q.setTextEN("Which of this Cancellors ruled under Dr. Heinz Fischer?");
			q.setMaxTime(new BigDecimal(30));
			SelectQueryBuilder chancellorQuery = DBPediaService.createQueryBuilder()
					.addWhereClause(DBPProp.createResource("title"), DBPedia.createProperty("Chancellor_of_Austria"))
					.addWhereClause(DBPediaOWL.president, DBPedia.createProperty("Heinz_Fischer"))
					.addPredicateExistsClause(DBPProp.createResource("name"))
					.addFilterClause(RDFS.label, Locale.GERMAN)
					.addFilterClause(RDFS.label, Locale.ENGLISH)
					.setLimit(2);
			Model chancellor = DBPediaService.loadStatements(chancellorQuery.toQueryString());
			List<String> chancellorEN =
					DBPediaService.getResourceNames(chancellor, Locale.ENGLISH);
			List<String> chancellorDE =
					DBPediaService.getResourceNames(chancellor, Locale.GERMAN);
			for(int y = 0; y < chancellorEN.size(); y++){
				Choice choice = new Choice();
				choice.setTextEN(chancellorEN.get(y));
				choice.setTextDE(chancellorDE.get(y));
				q.addRightChoice(choice);
			}
			SelectQueryBuilder otherChancellorQuery = DBPediaService.createQueryBuilder()
					.addWhereClause(DBPProp.createResource("title"), DBPedia.createProperty("Chancellor_of_Austria"))
					.addWhereClause(DBPediaOWL.president, DBPedia.createProperty("Thomas_Klestil"))
					.addPredicateExistsClause(DBPProp.createResource("name"))
					.addFilterClause(RDFS.label, Locale.GERMAN)
					.addFilterClause(RDFS.label, Locale.ENGLISH)
					.setLimit(2);
			Model otherChancellor = DBPediaService.loadStatements(otherChancellorQuery.toQueryString());
			List<String> otherChancellorEN =
					DBPediaService.getResourceNames(otherChancellor, Locale.ENGLISH);
			List<String> otherChancellorDE =
					DBPediaService.getResourceNames(otherChancellor, Locale.GERMAN);
			for(int y = 0; y < otherChancellorEN.size(); y++){
				Choice choice = new Choice();
				choice.setTextEN(otherChancellorEN.get(y));
				choice.setTextDE(otherChancellorDE.get(y));
				q.addWrongChoice(choice);
			}
		} else if(i == 2){
			q.setTextDE("Welche der folgenden Länder hat die selbe Währung wie Österreich?");
			q.setTextEN("Which of these countries has the same currency as Austria?");
			q.setMaxTime(new BigDecimal(30));
			// build SPARQL-query
			SelectQueryBuilder sameCurrencyQuery = DBPediaService.createQueryBuilder()
					.addWhereClause(DBPediaOWL.currency, DBPedia.createProperty("Euro"))
					.setLimit(3);
			// retrieve data from dbpedia
			Model sameCurrency = DBPediaService.loadStatements(sameCurrencyQuery.toQueryString());
			List<String> sameCurrencyCountriesEN =
					DBPediaService.getResourceNames(sameCurrency, Locale.ENGLISH);
			List<String> sameCurrencyCountriesDE =
					DBPediaService.getResourceNames(sameCurrency, Locale.GERMAN);
			for(int y = 0; y < sameCurrencyCountriesEN.size(); y++){
				Choice choice = new Choice();
				choice.setTextEN(sameCurrencyCountriesEN.get(y));
				choice.setTextDE(sameCurrencyCountriesDE.get(y));
				q.addRightChoice(choice);
			}
			SelectQueryBuilder someCurrencyQuery = DBPediaService.createQueryBuilder()
					.addWhereClause(DBPediaOWL.governmentType, DBPedia.createProperty("Federalism"))
					.addMinusClause(DBPediaOWL.currency, DBPedia.createProperty("Euro"))
					.setLimit(3);
			Model someCurrency = DBPediaService.loadStatements(someCurrencyQuery.toQueryString());
			List<String> someCurrencyCountriesEN =
					DBPediaService.getResourceNames(someCurrency, Locale.ENGLISH);
			List<String> someCurrencyCountriesDE =
					DBPediaService.getResourceNames(someCurrency, Locale.GERMAN);
			for(int y = 0; y < someCurrencyCountriesEN.size(); y++){
				Choice choice = new Choice();
				choice.setTextEN(someCurrencyCountriesEN.get(y));
				choice.setTextDE(someCurrencyCountriesDE.get(y));
				q.addWrongChoice(choice);
			}
		} else if(i == 1){
			q.setTextDE("In welchem der folgenen Länder wird die selbe Sprache wie in Österreich gesprochen?");
			q.setTextEN("In which of this countries do people speak the same language as in Austria?");
			q.setMaxTime(new BigDecimal(30));
			// build SPARQL-query
			SelectQueryBuilder sameLanguageQuery = DBPediaService.createQueryBuilder()
					.addWhereClause(DBPediaOWL.officialLanguage, DBPedia.createProperty("German_language"))
					.setLimit(3);
			// retrieve data from dbpedia
			Model sameLanguage = DBPediaService.loadStatements(sameLanguageQuery.toQueryString());
			List<String> sameLanguageCountriesEN =
					DBPediaService.getResourceNames(sameLanguage, Locale.ENGLISH);
			List<String> sameLanguageCountriesDE =
					DBPediaService.getResourceNames(sameLanguage, Locale.GERMAN);
			for(int y = 0; y < sameLanguageCountriesEN.size(); y++){
				Choice choice = new Choice();
				choice.setTextEN(sameLanguageCountriesEN.get(y));
				choice.setTextDE(sameLanguageCountriesDE.get(y));
				q.addRightChoice(choice);
			}
			SelectQueryBuilder someLanguageQuery = DBPediaService.createQueryBuilder()
					.addWhereClause(DBPediaOWL.governmentType, DBPedia.createProperty("Federalism"))
					.addMinusClause(DBPediaOWL.officialLanguage, DBPedia.createProperty("German_language"))
					.setLimit(3);
			Model someLanguage = DBPediaService.loadStatements(someLanguageQuery.toQueryString());
			List<String> someLanguageCountriesEN =
					DBPediaService.getResourceNames(someLanguage, Locale.ENGLISH);
			List<String> someLanguageCountriesDE =
					DBPediaService.getResourceNames(someLanguage, Locale.GERMAN);
			for(int y = 0; y < someLanguageCountriesEN.size(); y++){
				Choice choice = new Choice();
				choice.setTextEN(someLanguageCountriesEN.get(y));
				choice.setTextDE(someLanguageCountriesDE.get(y));
				q.addWrongChoice(choice);
			}
		} else{
			q.setTextDE("Welche Städte liegen in Österreich?");
			q.setTextEN("Which Cities are located in Austria?");
			q.setMaxTime(new BigDecimal(30));
			// build SPARQL-query
			SelectQueryBuilder autCitiesQuery = DBPediaService.createQueryBuilder()
					.addWhereClause(RDF.type,DBPediaOWL.PopulatedPlace)
					.addWhereClause(DBPediaOWL.country, DBPedia.createProperty("Austria"))
					.setLimit(2);
			// retrieve data from dbpedia
			Model autCities = DBPediaService.loadStatements(autCitiesQuery.toQueryString());
			List<String> autCitiesEN =
					DBPediaService.getResourceNames(autCities, Locale.ENGLISH);
			List<String> autCitiesDE =
					DBPediaService.getResourceNames(autCities, Locale.GERMAN);
			for(int y = 0; y < autCitiesEN.size(); y++){
				Choice choice = new Choice();
				choice.setTextEN(autCitiesEN.get(y));
				choice.setTextDE(autCitiesDE.get(y));
				q.addRightChoice(choice);
			}
			// build SPARQL-query
			SelectQueryBuilder notAutCitiesQuery = DBPediaService.createQueryBuilder()
					.addWhereClause(RDF.type,DBPediaOWL.PopulatedPlace)
					.addMinusClause(DBPediaOWL.country, DBPedia.createProperty("Austria"))
					.setLimit(4);
			// retrieve data from dbpedia
			Model notAutCities = DBPediaService.loadStatements(notAutCitiesQuery.toQueryString());
			List<String> notAutCitiesEN =
					DBPediaService.getResourceNames(notAutCities, Locale.ENGLISH);
			List<String> notAutCitiesDE =
					DBPediaService.getResourceNames(notAutCities, Locale.GERMAN);
			for(int y = 0; y < notAutCitiesEN.size(); y++){
					Choice choice = new Choice();
					choice.setTextEN(notAutCitiesEN.get(y));
					choice.setTextDE(notAutCitiesDE.get(y));
					q.addRightChoice(choice);
			}
		}
		return q;
	}
	
	private static Category addQuestions(Category c){
		//for(int i = 4; i > 0; i--){
			Question q = new Question();
			q.setTextDE("Wer war nie österreichischer Präsident?");
			q.setTextEN("Who has not been an austrian president?");
			q.setMaxTime(new BigDecimal(30));
			Choice choice = new Choice();
			choice.setTextDE("RICHTIG");
			choice.setTextEN("RIGHT");
			choice.setCorrectAnswer(true);
			q.addRightChoice(choice);
			//TODO Create 5 Questions
			c.addQuestion(q);
			//Logger.info(c.getQuestions().get(0).getTextDE().toString()+"    -worked?");
		//}
		// Resource Tim Burton is available at http://dbpedia.org/resource/Tim_Burton
		// Load all statements as we need to get the name later
		Resource director = DBPediaService.loadStatements(DBPedia.createResource("Tim_Burton"));
		// Resource Johnny Depp is available at http://dbpedia.org/resource/Johnny_Depp
		// Load all statements as we need to get the name later
		Resource actor = DBPediaService.loadStatements(DBPedia.createResource("Johnny_Depp"));
		// retrieve english and german names, might be used for question text
		String englishDirectorName = DBPediaService.getResourceName(director, Locale.ENGLISH);
		String germanDirectorName = DBPediaService.getResourceName(director, Locale.GERMAN);
		String englishActorName = DBPediaService.getResourceName(actor, Locale.ENGLISH);
		String germanActorName = DBPediaService.getResourceName(actor, Locale.GERMAN);
		// build SPARQL-query
		SelectQueryBuilder movieQuery = DBPediaService.createQueryBuilder()
		.setLimit(5) // at most five statements
		.addWhereClause(RDF.type, DBPediaOWL.Film)
		.addPredicateExistsClause(FOAF.name)
		.addWhereClause(DBPediaOWL.director, director)
		.addFilterClause(RDFS.label, Locale.GERMAN)
		.addFilterClause(RDFS.label, Locale.ENGLISH);
		// retrieve data from dbpedia
		Model timBurtonMovies = DBPediaService.loadStatements(movieQuery.toQueryString());
		// get english and german movie names, e.g., for right choices
		List<String> englishTimBurtonMovieNames =
		DBPediaService.getResourceNames(timBurtonMovies, Locale.ENGLISH);
		List<String> germanTimBurtonMovieNames =
		DBPediaService.getResourceNames(timBurtonMovies, Locale.GERMAN);
		return c;
	}
	
}
