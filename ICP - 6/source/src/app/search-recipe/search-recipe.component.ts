import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from './../../environments/environment';

@Component({
  selector: 'app-recipes',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css'],
})
export class SearchRecipeComponent implements OnInit {
  public itemName: string;
  public recipesList = [];
  public restaurants = [];
  public cityName = '';
  private APP_ID = environment.APP_ID;
  private APP_KEY = environment.APP_KEY;
  private food_url = `https://api.edamam.com/search?app_id=${this.APP_ID}&app_key=${this.APP_KEY}`;

  private CLIENT_ID = environment.CLIENT_ID;
  private CLIENT_SECRET = environment.CLIENT_SECRET_KEY;
  private place_url = `https://api.foursquare.com/v2/venues/explore?v=20180323
      &client_id=${this.CLIENT_ID}&client_secret=${this.CLIENT_SECRET}`;

  constructor(private http: HttpClient) {}

  ngOnInit() {}

  getRecipeDetails() {
    this.restaurants = [];
    this.http.get(this.food_url + `&q=${this.itemName}`).subscribe((data) => {
      const recipes = data['hits'];
      console.log(recipes);
      recipes.map(recipe => {
        console.log(recipe);
        const recipeObj = {
          name : recipe['recipe']['label'],
          url : recipe['recipe']['url'],
          icon : recipe['recipe']['image'],
          ingredients: recipe['recipe']['ingredientLines'].slice(0, 4)
        };
        this.recipesList.push(recipeObj);
      });
    });

    this.http.get(this.place_url + `&near=${this.cityName}&query=${this.itemName}`)
      .subscribe((data) => {
        const response = data['response'];
        const items = response['groups'][0]['items'].slice(0, 5);
        items.map((item) => {
          const venueObj = {
            id: item['venue']['id'],
            name: item['venue']['name'],
            address: {
              street: item['venue']['location']['address'],
              city: item['venue']['location']['city'],
              state: item['venue']['location']['state'],
              postalCode: item['venue']['location']['postalCode'],
              country: item['venue']['location']['country'],
            },
            category: item['venue']['categories'][0]['name'],
          };
          this.restaurants.push(venueObj);
        });
      });
  }
}
