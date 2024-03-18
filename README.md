Music Metada service

## Prerequisites

- Java 17
- Maven
- MySQL
## Getting Started

1. **Clone the repository:**

    ```bash
    git clone https://github.com/toniageorge/music-metadata-service.git
    ```
2. **Configure the Database:**
      Create a MySQL database
      Update the `application.yaml` file with your database  details.
    *Apply Schema**: Execute the following SQL commands to create the necessary tables:

    ```sql
    CREATE TABLE artist (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        is_artist_of_the_day BOOLEAN
    );

    CREATE TABLE track (
        id SERIAL PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        genre VARCHAR(100),
        length INT,
        artist_id INT REFERENCES artist(id)
    );
    ```
4. **Build and Run the Application:**

    ```bash
    cd music-metadata-service
    mvn spring-boot:run
    ```

The application will start at `http://localhost:8080`.

 5. **Swagger UI:**
   http://localhost:8080/swagger-ui/index.html#/

 6. **api endPoints**

 **Add a Track**
    Endpoint: POST /musics/tracks
    Request Body: TrackDTO
    Description: Adds a new track.
    Response: Returns the saved TrackDTO with HTTP status 201 (Created).

**Add an Artist**
  Endpoint: POST /musics/artists
  Request Body: ArtistDTO
  Description: Adds a new artist.
  Response: Returns the ID of the saved artist with HTTP status 201 (Created).
  
  **Update Artist Name**
  Endpoint: PUT /musics/artist/{artistId}
  Path Variable: artistId (ID of the artist)
  Request Parameter: newName (New name for the artist)
  Description: Updates the name of the artist with the specified ID.
  Response: Returns HTTP status 200 (OK) if successful.
  
  **Get Tracks by Artist**
  Endpoint: GET /musics/artists/{artistId}/tracks
  Path Variable: artistId (ID of the artist)
  Description: Retrieves all tracks associated with the specified artist.
  Response: Returns a list of TrackDTO objects.
  
  **Fetch Artist of the Day**
  Endpoint: GET /musics/artist-of-the-day
  Description: Retrieves the "Artist of the Day".
  Response: Returns the ArtistDTO object representing the Artist of the Day.
