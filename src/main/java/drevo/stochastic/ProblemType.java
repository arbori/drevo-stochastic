/*
 * Copyright (C) 2024 Marcelo Arbori Nogueira - marcelo.arbori@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package drevo.stochastic;

public enum ProblemType {
    MINIMIZE(1.0), MAXIMIZE(-1.0);

    private double value;

    ProblemType(double value) { this.value = value; }

    public double valueOf() { return value; }
}
