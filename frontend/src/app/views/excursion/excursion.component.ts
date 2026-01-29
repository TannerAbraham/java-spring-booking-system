import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router, ActivatedRoute, ParamMap} from '@angular/router';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Excursion} from '../../model/excursion';
import {Vacation} from 'src/app/model/vacation';

@Component({
  selector: 'app-excursion',
  templateUrl: './excursion.component.html',
  styleUrls: ['./excursion.component.css']
})
export class ExcursionComponent implements OnInit {

  vacationUrl = 'http://localhost:8080/api/vacations/';
  excursions: Excursion[] = [];
  vacationId: number = 0;
  vacationTitle: string = ''

  constructor(private http: HttpClient, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.vacationId = +this.route.snapshot.paramMap.get('vacationId')!;
    console.log('===== EXCURSION COMPONENT INIT =====');
    console.log('Vacation ID:', this.vacationId);

    // Get vacation title
    this.getVacationTitle().subscribe(
      title => {
        this.vacationTitle = title;
        console.log('Vacation title:', title);
      },
      error => {
        console.error('Error loading vacation title:', error);
      }
    );

    // Get excursions
    this.getExcursions().subscribe(
      excursions => {
        console.log('Received excursions:', excursions);
        console.log('Is array?', Array.isArray(excursions));
        console.log('Count:', excursions.length);
        
        if (!Array.isArray(excursions)) {
          console.error('ERROR: Excursions is not an array!');
          console.error('Type:', typeof excursions);
          console.error('Value:', excursions);
          return;
        }
        
        if (excursions.length === 0) {
          console.warn('WARNING: No excursions found for vacation', this.vacationId);
          return;
        }

        // Parse IDs from HATEOAS links
        excursions.forEach(excursion => {
          if (excursion._links && excursion._links.self && excursion._links.self.href) {
            let parsedId = excursion._links.self.href.split("/")[5];
            excursion.id = parseInt(parsedId);
            console.log('Parsed excursion ID:', excursion.id, 'for', excursion.excursion_title);
          }
        });

        // Sort by title
        this.excursions = excursions.sort(function (a, b) {
          if (a.excursion_title < b.excursion_title) return -1;
          if (a.excursion_title > b.excursion_title) return 1;
          return 0;
        });
        
        console.log('Final sorted excursions:', this.excursions);
        console.log('===================================');
      },
      error => {
        console.error('===== ERROR LOADING EXCURSIONS =====');
        console.error('Status:', error.status);
        console.error('Message:', error.message);
        console.error('Full error:', error);
        console.error('====================================');
      }
    );
  }

  getExcursions(): Observable<Excursion[]> {
    // Use custom controller endpoint - returns simple array
    const url = `http://localhost:8080/api/vacations/${this.vacationId}/excursions`;
    console.log('Fetching from URL:', url);
    
    return this.http.get<Excursion[]>(url).pipe(
      map(response => {
        console.log('Raw HTTP response:', response);
        return response;
      })
    );
  }

  getVacationTitle(): Observable<string> {
    const url = this.vacationUrl + this.vacationId;
    console.log('Fetching vacation from:', url);
    
    return this.http.get<Vacation>(url).pipe(
      map(response => {
        console.log('Vacation response:', response);
        return response.vacation_title;
      })
    );
  }
}